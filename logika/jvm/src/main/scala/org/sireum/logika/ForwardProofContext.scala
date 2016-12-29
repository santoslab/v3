/*
 Copyright (c) 2016, Robby, Kansas State University
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

package org.sireum.logika

import org.sireum.util._

private final case class
ForwardProofContext(unitNode: ast.Program,
                    autoEnabled: Boolean,
                    timeoutInMs: PosInteger,
                    checkSat: Boolean,
                    hintEnabled: Boolean,
                    inscribeSummoningsEnabled: Boolean,
                    coneInfluenceEnabled: Boolean,
                    invariants: ILinkedSet[ast.Exp] = ilinkedSetEmpty,
                    premises: ILinkedSet[ast.Exp] = ilinkedSetEmpty,
                    vars: ISet[String] = isetEmpty,
                    facts: IMap[String, ast.Exp] = imapEmpty,
                    provedSteps: IMap[Natural, ast.ProofStep] = imapEmpty,
                    declaredStepNumbers: IMap[Natural, LocationInfo] = imapEmpty,
                    mdOpt: Option[ast.MethodDecl] = None,
                    satFacts: Boolean = true)
                   (implicit reporter: AccumulatingTagReporter) extends ProofContext[ForwardProofContext] {
  val isSymExe = false
  val bitWidth = 0

  def check: Boolean = {
    val program = unitNode
    val facts = extractFacts
    var isSat = true
    if (facts.nonEmpty &&
      !checkSat("Facts", nodeLocMap(program), facts.values, genMessage = true,
        unsatMsg = "The specified set of facts are unsatisfiable.",
        unknownMsg = {
          isSat = false
          "The set of facts might not be satisfiable."
        },
        timeoutMsg = {
          isSat = false
          "Could not check satisfiability of the set of facts due to timeout."
        }
      )) return false
    copy(facts = facts, satFacts = isSat).check(program.block)
    !reporter.hasError
  }

  def oldId(id: ast.Id): ast.Id = ast.Exp.Id(id.tipe, s"${id.value}_old")

  def check(block: ast.Block, checkReturn: Boolean = false): Option[ForwardProofContext] = {
    var pcOpt: Option[ForwardProofContext] = Some(this)
    for (stmt <- block.stmts if pcOpt.isDefined) {
      val pc =
        if (stmt.isInstanceOf[ast.ProofStmt]) pcOpt.get
        else pcOpt.get.cleanup
      pcOpt = pc.check(stmt)
    }
    (pcOpt, block.returnOpt) match {
      case (Some(pc), Some(ret)) =>
        pc.checkPostCondition(nodeLocMap(ret), ret.expOpt)
        pcOpt = None
      case (Some(pc), _) if checkReturn =>
        val li = nodeLocMap(block)
        pc.checkPostCondition(li.copy(
          lineBegin = li.lineEnd,
          columnBegin = li.columnEnd - 1,
          offset = li.offset + li.length - 1,
          length = 1), None)
        pcOpt = None
      case _ =>
    }
    pcOpt
  }

  def checkPostCondition(li: LocationInfo, retExpOpt: Option[ast.Exp]): Unit = {
    assert(mdOpt.nonEmpty)
    val md = mdOpt.get
    val invs = if (md.isHelper) ilinkedSetEmpty else invariants
    val modifiedIds = md.contract.modifies.ids.toSet
    var modifiedInvariants = ilinkedSetEmpty[ast.Exp]
    for (e <- invs) {
      var modified = false
      Visitor.build({
        case id: ast.Id =>
          if (modifiedIds.contains(id)) {
            modified = true
          }
          false
      })(e)
      if (modified) modifiedInvariants += e
    }
    if (autoEnabled) {
      val ps = premises ++ facts.values
      for (e <- modifiedInvariants)
        if (!isValid(s"Global Invariant", li, ps, ivector(e))) {
          val eLi = nodeLocMap(e)
          error(li, s"Could not automatically deduce the global invariant specified at [${eLi.lineBegin}, ${eLi.columnBegin}].")
        }
    } else {
      for (e <- modifiedInvariants)
        if (!premises.contains(e)) {
          val eLi = nodeLocMap(e)
          error(li, s"The global invariant specified at [${eLi.lineBegin}, ${eLi.columnBegin}] has not been proven.")
        }
    }
    val post = md.contract.ensures.exps
    val postSubstMap = retExpOpt match {
      case Some(e) => imapEmpty[ast.Node, ast.Node] + (ast.Result() -> e)
      case _ => imapEmpty[ast.Node, ast.Node]
    }
    if (autoEnabled) {
      val ps = premises ++ facts.values
      for (e <- post)
        if (!isValid("Post-condition", li, ps, ivector(subst(e, postSubstMap)))) {
          val eLi = nodeLocMap(e)
          error(li, s"Could not automatically deduce the post-condition specified at [${eLi.lineBegin}, ${eLi.columnBegin}].")
        }
    } else {
      for (e <- post)
        if (!premises.contains(subst(e, postSubstMap))) {
          val eLi = nodeLocMap(e)
          error(e, s"The post-condition specified at [${eLi.lineBegin}, ${eLi.columnBegin}] has not been proven.")
        }
    }
  }

  def check(stmt: ast.Stmt): Option[ForwardProofContext] = {
    val effectiveSatFacts = if (satFacts) facts.values else ivectorEmpty
    var hasError = false
    if (!stmt.isInstanceOf[ast.ProofElementStmt] &&
      !stmt.isInstanceOf[ast.MethodDecl]) {
      hasError = hasRuntimeError(stmt) || hasError
    }
    val pcOpt = stmt match {
      case ast.ProofStmt(proof) =>
        check(proof).map(_.copy(
          premises = filter(premises ++ extractClaims(proof, reverse = false)),
          provedSteps = imapEmpty))
      case ast.SequentStmt(sequent) =>
        if (sequent.premises.nonEmpty) {
          if (!isValid("Sequent Premises", nodeLocMap(stmt), premises, sequent.premises)) {
            hasError = true
            error(stmt, "Could not automatically deduce the specified sequent's premises.")
          }
          if (!isValid("Sequent Conclusions", nodeLocMap(stmt), sequent.premises, sequent.conclusions)) {
            hasError = true
            error(stmt, "Could not automatically deduce the specified sequent's conclusions from its premises.")
          }
        } else if (!isValid("Sequent Conclusions", nodeLocMap(stmt), premises ++ facts.values, sequent.conclusions)) {
          hasError = true
          error(stmt, "Could not automatically deduce the specified sequent's conclusions.")
        }
        Some(copy(premises =
          filter(if (autoEnabled) premises else ilinkedSetEmpty) ++
            sequent.premises ++ sequent.conclusions))
      case ast.Assert(e) =>
        if (autoEnabled) {
          if (!isValid("Assertion", nodeLocMap(stmt), premises ++ facts.values, ivector(e))) {
            error(stmt, s"Could not automatically deduce the assertion validity.")
            hasError = true
            checkSat("Assertion", nodeLocMap(stmt), premises ++ effectiveSatFacts + e, genMessage = true,
              unsatMsg = s"The assertion is unsatisfiable.",
              unknownMsg = s"The assertion might not be satisfiable.",
              timeoutMsg = s"Could not check satisfiability of the assertion due to timeout.")
          }
        } else {
          if (!premises.contains(e)) {
            error(e, s"The assertion has not been proven.")
            hasError = true
            checkSat("Assertion", nodeLocMap(stmt), premises ++ effectiveSatFacts + e, genMessage = true,
              unsatMsg = s"The assertion is unsatisfiable.",
              unknownMsg = s"The assertion might not be satisfiable.",
              timeoutMsg = s"Could not check satisfiability of the assertion due to timeout.")
          }
        }
        Some(copy(premises = premises + e))
      case ast.Assume(e) =>
        hasError = !checkSat("Assumption", nodeLocMap(stmt),
          premises ++ effectiveSatFacts + e, genMessage = true,
          unsatMsg = s"The assumption is unsatisfiable.",
          unknownMsg = s"The assumption might not be satisfiable.",
          timeoutMsg = s"Could not check satisfiability of the assumption due to timeout."
        ) || hasError
        Some(copy(premises = premises + e))
      case ast.SeqAssign(id, index, exp) =>
        val old = oldId(id)
        val m = imapEmpty[ast.Node, ast.Node] + (id -> old)
        import ast.Exp
        val qVar = Exp.Id(tipe.Z, "q_i")
        val t = id.tipe.asInstanceOf[tipe.Fn].result
        Some(copy(premises =
          premises.map(e => subst(e, m)) ++
            ivector(
              Exp.Eq(tipe.Z, Exp.Size(id.tipe, id), Exp.Size(old.tipe, old)),
              Exp.Eq(t, Exp.Apply(id.tipe, id, ast.Node.seq(subst(index, m))), subst(exp, m)),
              ast.ForAll(
                ast.Node.seq(qVar),
                Some(ast.RangeDomain(Checker.zero, Exp.Size(id.tipe, id),
                  loLt = false, hiLt = true)),
                Exp.Implies(tipe.B,
                  Exp.Ne(tipe.Z, qVar, index),
                  Exp.Eq(t, Exp.Apply(id.tipe, id, ast.Node.seq(qVar)),
                    Exp.Apply(old.tipe, old, ast.Node.seq(qVar)))
                )
              )
            )))
      case ast.ExpStmt(exp) =>
        val (he, pc2) = invoke(exp, None)
        hasError ||= he
        Some(pc2)
      case a: ast.VarAssign =>
        val id = a.id
        val exp = a.exp
        exp match {
          case _: ast.ReadInt => assign(id)
          case _: ast.RandomInt => assign(id)
          case exp: ast.Clone => assign(id, exp.id)
          case exp: ast.Apply if !exp.expTipe.isInstanceOf[tipe.MSeq] =>
            val (he, pc2) = invoke(exp, Some(id))
            hasError ||= he
            Some(pc2)
          case _ => assign(id, exp)
        }
      case ast.If(exp, thenBlock, elseBlock) =>
        val thenPcOpt = copy(premises = premises + exp).check(thenBlock)
        val elsePcOpt = copy(premises = premises + ast.Exp.Not(tipe.B, exp)).check(elseBlock)
        (thenBlock.returnOpt.isEmpty, elseBlock.returnOpt.isEmpty) match {
          case (true, true) => (thenPcOpt, elsePcOpt) match {
            case (Some(thenPc), Some(elsePc)) =>
              if (autoEnabled) {
                val thenPremises = thenPc.cleanup.premises
                val elsePremises = elsePc.cleanup.premises
                val commonPremises = thenPremises.intersect(elsePremises)
                import ast.Exp
                Some(copy(premises = commonPremises +
                  Exp.Or(tipe.B, Exp.And((thenPremises -- commonPremises).toVector),
                    Exp.And((elsePremises -- commonPremises).toVector))))
              } else {
                Some(copy(premises =
                  orClaims(thenPc.cleanup.premises, elsePc.cleanup.premises)))
              }
            case _ => None
          }
          case (true, false) => thenPcOpt match {
            case Some(thenPc) => Some(copy(premises = thenPc.cleanup.premises))
            case _ => None
          }
          case (false, true) => elsePcOpt match {
            case Some(elsePc) => Some(copy(premises = elsePc.cleanup.premises))
            case _ => None
          }
          case (false, false) => None
        }
      case stmt: ast.MethodDecl =>
        val invs = if (stmt.isHelper) ilinkedSetEmpty else invariants
        val effectivePre = invs ++ stmt.contract.requires.exps
        val effectivePost = invs ++ stmt.contract.ensures.exps
        val preLi = nodeLocMap(
          if (stmt.contract.requires.exps.isEmpty) stmt
          else stmt.contract.requires.exps.head)
        hasError =
          !checkSat("Effective Pre-condition", preLi,
            effectiveSatFacts ++ effectivePre, genMessage = true,
            unsatMsg = s"The effective pre-condition of method ${
              stmt.id.value
            } is unsatisfiable.",
            unknownMsg = s"The effective pre-condition of method ${
              stmt.id.value
            } might not be satisfiable.",
            timeoutMsg = s"Could not check satisfiability of the effective pre-condition of method ${
              stmt.id.value
            } due to timeout."
          ) || hasError
        val postLi = nodeLocMap(
          if (stmt.contract.ensures.exps.isEmpty) stmt
          else stmt.contract.ensures.exps.head)
        hasError =
          !checkSat("Effective Post-condition", postLi,
            effectiveSatFacts ++ effectivePost, genMessage = true,
            unsatMsg = s"The effective post-condition of method ${
              stmt.id.value
            } is unsatisfiable.",
            unknownMsg = s"The effective post-condition of method ${
              stmt.id.value
            } might not be satisfiable.",
            timeoutMsg = s"Could not check satisfiability of the effective post-condition of method ${
              stmt.id.value
            } due to timeout."
          ) || hasError
        val modifiedIds = stmt.contract.modifies.ids.toSet
        val mods = modifiedIds.map(id =>
          ast.Exp.Eq(id.tipe, id, ast.Exp.Id(id.tipe, id.value + "_in")))
        copy(premises = ilinkedSetEmpty ++ effectivePre ++ mods, mdOpt = Some(stmt)).
          check(stmt.block, checkReturn = true)
        Some(this.cleanup)
      case ast.InvStmt(inv) =>
        if (autoEnabled) {
          val ps = premises ++ facts.values
          for (e <- inv.exps)
            if (!isValid("Global Invariant", nodeLocMap(e), ps, ivector(e))) {
              error(e, s"Could not automatically deduce the global invariant.")
              hasError = true
            }
        } else {
          for (e <- inv.exps)
            if (!premises.contains(e)) {
              error(e, s"The global invariant has not been proven.")
              hasError = true
            }
        }
        if (hasError)
          checkSat("Global Invariant", nodeLocMap(stmt),
            effectiveSatFacts ++ inv.exps, genMessage = true,
            unsatMsg = s"The global invariant(s) are unsatisfiable.",
            unknownMsg = s"The global invariant(s) might not be satisfiable.",
            timeoutMsg = s"Could not check satisfiability of the global invariant(s) due to timeout.")
        Some(copy(invariants = invariants ++ inv.exps))
      case _: ast.FactStmt => Some(this)
      case stmt@ast.While(exp, loopBlock, loopInv) =>
        val es = loopInv.invariant.exps
        if (autoEnabled) {
          val ps = premises ++ facts.values
          for (e <- es)
            if (!isValid("Loop Invariant (beginning)", nodeLocMap(e), ps, ivector(e))) {
              error(e, s"Could not automatically deduce the loop invariant at the beginning of the loop.")
              hasError = true
            }
        } else {
          for (e <- es)
            if (!premises.contains(e)) {
              error(e, s"The loop invariant has not been proved at the beginning of the loop.")
              hasError = true
            }
        }
        var ps = ilinkedSetEmpty ++ es
        var modifiedIds = loopInv.modifies.ids.toSet
        for (premise <- premises) {
          var propagate = true
          lazy val v: Any => Boolean = Visitor.build({
            case n: ast.Quant[_] if n.ids.exists(modifiedIds.contains(_)) =>
              val oldModifiedIds = modifiedIds
              modifiedIds --= n.ids
              v(n)
              modifiedIds = oldModifiedIds
              false
            case id: ast.Id =>
              if (modifiedIds.contains(id)) propagate = false
              false
          })
          v(premise)
          if (propagate) ps += premise
        }
        copy(premises = ps + exp).check(loopBlock) match {
          case Some(pc2) =>
            hasError = hasError || pc2.hasRuntimeError(stmt)
            if (autoEnabled) {
              val ps = pc2.premises ++ pc2.facts.values
              for (e <- es)
                if (!isValid("Loop Invariant (end)", nodeLocMap(e), ps, ivector(e))) {
                  error(e, s"Could not deduce the loop invariant at the end of the loop.")
                  hasError = true
                }
            } else {
              for (e <- es)
                if (!pc2.premises.contains(e)) {
                  error(e, s"The loop invariant has not been proved at the end of the loop.")
                  hasError = true
                }
            }
          case _ =>
        }
        Some(copy(premises = ps + ast.Exp.Not(tipe.B, exp)))
      case _: ast.Print => Some(this)
    }
    generateHint(premises, stmt,
      pcOpt.map(_.premises).getOrElse(ilinkedSetEmpty))
    if (hasError) None else pcOpt
  }

  def assign(id: ast.Id, exp: ast.Exp): Option[ForwardProofContext] = {
    val sst = expRewriter(Map[ast.Node, ast.Node](id -> oldId(id)))
    Some(copy(premises = premises.map(sst) + ast.Exp.Eq(id.tipe, id, sst(exp))))
  }

  def assign(id: ast.Id): Option[ForwardProofContext] = {
    val sst = expRewriter(Map[ast.Node, ast.Node](id -> oldId(id)))
    Some(copy(premises = premises.map(sst)))
  }

  def invoke(a: ast.Apply, lhsOpt: Option[ast.Id]): (Boolean, ForwardProofContext) = {
    var hasError = false
    val md = a.declOpt.get
    var postSubstMap = md.params.map(_.id).zip(a.args).toMap[ast.Node, ast.Node]
    val isHelper = md.isHelper
    var invs = ivectorEmpty[ast.Exp]
    val modIds = md.contract.modifies.ids.map(_.value).toSet
    for (inv <- invariants if !isHelper) {
      var mod = false
      Visitor.build({
        case id: ast.Id =>
          if (modIds.contains(id.value))
            mod = true
          false
      })(inv)
      if (mod) invs :+= inv
    }
    if (autoEnabled) {
      val ps = premises ++ facts.values
      for (inv <- invs if mdOpt.isDefined)
        if (!isValid("Global Invariant", nodeLocMap(a), ps, ivector(inv))) {
          val li = nodeLocMap(inv)
          error(a, s"Could not automatically deduce the invariant of method ${md.id.value} defined at [${li.lineBegin}, ${li.columnBegin}].")
          hasError = true
        }
      for (pre <- md.contract.requires.exps)
        if (!isValid("Pre-condition", nodeLocMap(a), ps, ivector(subst(pre, postSubstMap)))) {
          val li = nodeLocMap(pre)
          error(a, s"Could not automatically deduce the pre-condition of method ${md.id.value} defined at [${li.lineBegin}, ${li.columnBegin}].")
          hasError = true
        }
    } else {
      for (inv <- invs)
        if (!premises.contains(inv)) {
          val li = nodeLocMap(inv)
          error(a, s"The invariant defined at [${li.lineBegin}, ${li.columnBegin}] has not been proven.")
          hasError = true
        }
      for (pre <- md.contract.requires.exps)
        if (!premises.contains(subst(pre, postSubstMap))) {
          val li = nodeLocMap(pre)
          error(a, s"The pre-condition of method ${md.id.value} defined at [${li.lineBegin}, ${li.columnBegin}] has not been proven.")
          hasError = true
        }
    }
    val (lhs, psm) = lhsOpt match {
      case Some(x) =>
        (x, imapEmpty[ast.Node, ast.Node] + (x -> oldId(x)))
      case _ =>
        (ast.Exp.Id(md.id.tipe.asInstanceOf[tipe.Fn].result,
          md.id.value + "_result"),
          imapEmpty[ast.Node, ast.Node])
    }
    var premiseSubstMap = psm
    postSubstMap += ast.Result() -> lhs
    var modParams = isetEmpty[String]
    for ((p, arg@ast.Id(_)) <- md.params.map(_.id).zip(a.args) if modIds.contains(p.value)) {
      modParams += p.value
      val arg_old = oldId(arg)
      val p_in = ast.Exp.Id(p.tipe, p.value + "_in")
      premiseSubstMap += arg -> arg_old
      postSubstMap += arg -> arg_old
      postSubstMap += p -> arg
      postSubstMap += p_in -> arg_old
    }
    for (g <- md.contract.modifies.ids if !modParams.contains(g.value)) {
      val g_old = oldId(g)
      val g_in = ast.Exp.Id(g.tipe, g.value + "_in")
      premiseSubstMap += g -> g_old
      postSubstMap += g_in -> g_old
    }
    (hasError, make(premises =
      premises.map(e => subst(e, premiseSubstMap)) ++ invs ++
        md.contract.ensures.exps.map(e => subst(e, postSubstMap))
    ))
  }

  override def check(step: ast.RegularStep): Option[ForwardProofContext] = {
    val num = step.num.value
    step match {
      case ast.Premise(_, exp) =>
        if (premises.contains(exp) || exp == Checker.top) addProvedStep(step)
        else if (autoEnabled && deduce(num, exp, ivectorEmpty, isAuto = true)) addProvedStep(step)
        else error(exp, s"Could not find the claimed premise in step #$num.")
      case _ => super.check(step)
    }
  }

  def cleanup: ForwardProofContext =
    copy(premises = filter(premises), provedSteps = imapEmpty,
      declaredStepNumbers = imapEmpty)

  def filter(premises: ILinkedSet[ast.Exp]): ILinkedSet[ast.Exp] = {
    def keep(e: ast.Exp) = {
      var r = true
      Visitor.build({
        case ast.Id(value) if value.endsWith("_old") || value.endsWith("_result") =>
          r = false
          false
      })(e)
      r
    }

    premises.filter(keep)
  }

  def make(vars: ISet[String],
           provedSteps: IMap[Natural, ast.ProofStep],
           declaredStepNumbers: IMap[Natural, LocationInfo],
           premises: ILinkedSet[ast.Exp]): ForwardProofContext =
    copy(vars = vars, provedSteps = provedSteps,
      declaredStepNumbers = declaredStepNumbers, premises = premises)
}
