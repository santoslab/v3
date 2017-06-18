/*
 Copyright (c) 2017, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.lang.parser

import scala.collection.immutable.ListSet
import scala.meta._
import scala.meta.inputs.Input
import scala.meta.internal.parsers.{InfixMode, ScalametaParser}
import scala.meta.parsers.{Parse, ParseException, Parsed}
import scala.meta.tokenizers.TokenizeException
import scala.meta.tokens.Token.{Colon, Comma, Dot, Ident, LeftBracket, LeftParen, RightBracket, RightParen}

object LParser {
  val forallTokens = ListSet("∀", "A", "all", "forall")
  val existsTokens = ListSet("∃", "E", "some", "exists")
  val quantTokens: ListSet[String] = forallTokens ++ existsTokens
  val lStmtFirst = ListSet("requires", "theorem", "fact")

  lazy val parseTerm: Parse[Term] = toParse(_.parseTerm())

  implicit class ParseString(text: String)(implicit dialect: Dialect) {
    def parseFormula: Parsed[Term] = parseTerm(Input.String(text), dialect)
  }

  def toParse[T](fn: LParser => T): Parse[T] = (input, dialect) => {
    try {
      val parser = new LParser(input, dialect)
      Parsed.Success(fn(parser))
    } catch {
      case details@TokenizeException(pos, message) =>
        Parsed.Error(pos, message, details)
      case details@ParseException(pos, message) =>
        Parsed.Error(pos, message, details)
    }
  }
}

import LParser._

class LParser(input: Input, dialect: Dialect) extends ScalametaParser(input, dialect) {
  parser =>

  object loutPattern extends PatternContextSensitive {
    override def infixType(mode: InfixMode.Value): Type = compoundType()

    def argType(): Type = typ()

    def functionArgType(): Type.Arg = paramType()
  }

  /** {{{
   *  SimpleExpr     ::= QuantExpr
   *                   |  ... super.simpleExpr()
   *  }}}
   */
  override def simpleExpr(): Term = {
    if (token.is[Ident] && quantTokens.contains(token.text)) quantExpr()
    else super.simpleExpr()
  }

  /** {{{
   *  QuantExpr      ::= ( Id<∀> | Id<A> | Id<∃> | Id<E> ) Id {`,' Id} `:' Domain Expr
   *
   *  Domain         ::= ( `(' | `<' | `[' ) Expr ',' Expr ( `)' | `>' | `]' )
   *                   |  Type
   *  }}}
   */
  private def quantExpr(): Term = autoPos {
    acceptKw(quantTokens)
    val isForAll = forallTokens.contains(token.text)

    val ids: Term = identifiers()

    accept[Colon]

    if (token.is[LeftParen] || token.is[LeftBracket]) {
      val loExact = token.is[LeftBracket]
      next()
      val lo = expr()
      accept[Comma]
      val hi = expr()
      val hiExact = token.is[RightBracket]
      if (hiExact) next() else accept[RightParen]
      val e = expr()
      Term.Apply(
        Term.Name("L$Quant"),
        List[Term](
          Lit.Boolean(isForAll),
          ids,
          Term.Tuple(List(lo, Lit.Boolean(loExact), hi, Lit.Boolean(hiExact))),
          e)
      )
    } else {
      val t = loutPattern.typ()
      val e = expr()
      Term.Apply(
        Term.Name("L$Quant"),
        List[Term](
          Lit.Boolean(isForAll),
          Term.Ascribe(ids, t),
          e)
      )
    }
  }


  /** {{{
   *  DefContract    ::= [ Ident<requires> {nl} { NamedExpr {nl} } ]
   *                     [ Ident<modifies> {nl} Expr {`,' {nl} Expr} {nl} ]
   *                     [ Ident<ensures> {nl} { NamedExpr {nl} } ]
   *                     { SubContract {nl} }
   *
   *  NamedExpr      ::= [Ident `.' [nl]] Expr
   *
   *  SubContract    ::= def Ident `(' PureOpt Ident {`,' PureOpt Ident} `)' {nl} DefContract
   *
   *  PureOpt        ::= [`@' Ident<pure>]
   *
   *  SpecDefs       ::= { SpecDef {nl} }
   *
   *  SpecDef        ::= `=' NamedExpr [`,' ( [case Pattern] [Guard] | Ident<otherwise> ) ] {nl} [ WhereDefs ]
   *
   *  WhereDefs      ::= Ident<where> { {nl} WhereDef }
   *
   *  WhereDef       ::= val Ident `:' Type `=' Expr
   *                   |  def Ident `(' [Params] `)' `:' Type { {nl} SpecDef }
   *
   *  LoopInvMod     ::= [ Ident<invariant> {nl} { NamedExpr {nl} } ]
   *                     [ Ident<modifies> Expr {`,' Expr} {nl} ]
   *
   *  LClause        ::= Invariants
   *                   |  Facts
   *                   |  Theorems
   *                   |  Sequent
   *                   |  Proof
   *
   *  Invariants     ::= {nl} Ident<invariant> {nl} { NamedExpr {nl} }
   *
   *  Facts          ::= {nl} Ident<fact> {nl} { Fact {nl} }
   *
   *  Fact           ::= NamedExpr
   *
   *  Theorems       ::= {nl} Ident<theorem> {nl} { Theorem {nl} }
   *
   *  Theorem        ::= NamedExpr Sequent
   *
   *  Sequent        ::= {nl} Claims {nl} ( Ident<|-> | Ident<⊢> ) {nl} Claims {nl} [ Proof ]
   *
   *  Claims         ::= Expr {`,' {nl} Expr}
   *
   *  Proof          ::= {nl} `{' { {nl} ProofStep } {nl} `}' {nl}
   *
   *  ProofStep      ::= Int `.' Expr Just
   *                   |  Int `.' SubProof
   *
   *  SubProof       ::= `{' {nl} AssumeStep { {nl} ProofStep } {nl} `}'
   *
   *  AssumeStep     ::= Int `.' Expr Ident<assume>
   *                   |  Int `.' Ident `:' Type
   *                   |  Int `.' Ident `:' Type Expr Ident<assume>
   *
   *  Just           ::= Ident<premise>
   *                   |  ( Ident<∧> | Ident<^> ) Ident<i>                                  Int Int
   *                   |  ( Ident<∧> | Ident<^> ) Ident<e1>                                 Int
   *                   |  ( Ident<∧> | Ident<^> ) Ident<e2>                                 Int
   *                   |  ( Ident<∨> Ident<i1> | Ident<Vi1> )                               Int
   *                   |  ( Ident<∨> Ident<i2> | Ident<Vi2> )                               Int
   *                   |  ( Ident<∨> Ident<e> | Ident<Ve> )                                 Int Int Int
   *                   |  ( Ident<→> | Ident<->> ) Ident<i>                                 Int
   *                   |  ( Ident<→> | Ident<->> ) Ident<e>                                 Int Int
   *                   |  ( Ident<¬> | Ident<~> ) Ident<i>                                  Int
   *                   |  ( Ident<¬> | Ident<~> ) Ident<e>                                  Int Int
   *                   |  ( Ident<⊥> | Ident<_|_> ) Ident<e>                                Int
   *                   |  Ident<pbc>                                                        Int
   *                   |  ( Ident<∀> Ident<i> | Ident<Ai> | Ident<alli> | Ident<foralli> )  Int
   *                   |  ( Ident<∀> Ident<e> | Ident<Ae> | Ident<alle> | Ident<foralle> )  Int Expr {`,' Expr}
   *                   |  ( Ident<∃> Ident<i> | Ident<Ei> | Ident<somei> | Ident<existsi> ) Int Expr {`,' Expr}
   *                   |  ( Ident<∃> Ident<e> | Ident<Ee> | Ident<somee> | Ident<existse> ) Int Int {`,' Int Int}
   *                   |  Ident<fact>                                                       Int
   *                   |  Ident<invariant>
   *                   |  Ident<subst1>                                                     Int Int
   *                   |  Ident<subst2>                                                     Int Int
   *                   |  Ident<algebra>                                                    {Int}
   *                   |  Ident<auto>                                                       {Int}
   *  }}}
   */
  def lStmt(): Unit = {
  }

  def acceptKw(kws: ListSet[String]): String = {
    val text = token.text
    if (token.is[Ident] && kws.contains(text)) {
      val r = token.text
      next()
      r
    } else {
      reporter.syntaxError(s"${kws.mkString(" or ")} expected but $text found", at = token)
    }
  }

  def identifiers(): Term = {
    var t = token
    accept[Ident]
    var ids = List(atPos(t.start, t.end)(Term.Name(t.text)))
    while (token.is[Comma]) {
      next()
      t = token
      accept[Ident]
      ids ::= atPos(t.start, t.end)(Term.Name(t.text))
    }
    if (ids.size == 1) ids.head
    else Term.Tuple(ids.reverse)
  }
}
