// #Sireum
// @formatter:off

/*
 Copyright (c) 2019, Robby, Kansas State University
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

// This file is auto-generated from CliConfig.sc

package org.sireum.cli

import org.sireum._

object Cli {

  @datatype trait SireumTopOption

  @datatype class HelpOption extends SireumTopOption

  @datatype class LogikaOption(
    help: String,
    args: ISZ[String],
    formula: Option[String],
    server: B,
    auto: B,
    bitwidth: Z,
    c: Option[String],
    compare: B,
    last: B,
    run: B,
    sat: B,
    symexe: B,
    timeout: Z,
    ascii: B,
    unicode: B
  ) extends SireumTopOption

  @datatype class AwasOption(
    help: String,
    args: ISZ[String]
  ) extends SireumTopOption

  @datatype class SlangTipeOption(
    help: String,
    args: ISZ[String],
    sourcepath: ISZ[String],
    outline: B,
    force: ISZ[String],
    verbose: B,
    noRuntime: B,
    save: Option[String],
    load: Option[String],
    gzip: B
  ) extends SireumTopOption
}

import Cli._

@record class Cli(pathSep: C) {

  def parseSireum(args: ISZ[String], i: Z): Option[SireumTopOption] = {
    if (i >= args.size) {
      println(
        st"""Sireum: A Software Analysis Platform (v3)
            |(c) 2018, SAnToS Laboratory, Kansas State University
            |
            |Available modes:
            |logika                   Logika verifier and proof checker""".render
      )
      return Some(HelpOption())
    }
    val opt = select("sireum", args, i, ISZ("logika", "x"))
    opt match {
      case Some(string"logika") => parseLogika(args, i + 1)
      case Some(string"x") => parseX(args, i + 1)
      case _ => return None()
    }
  }

  def parseLogika(args: ISZ[String], i: Z): Option[SireumTopOption] = {
    val help =
      st"""Sireum/Logika:
          |A Program Verifier and A Natural Deduction Proof Checker
          |... for Propositional, Predicate, and Programming Logics
          |
          |Usage: <option>* <file>+
          |
          |Available Options:
          |-f, --formula            Formula/sequent matching the truth table/logic input
          |                           file's (expects a string)
          |    --server             Enable server mode
          |-h, --help               Display this information
          |
          |Programming Logic Options:
          |-a, --auto               Enable auto mode
          |-b, --bitwidth           Default integer bit-width for symbolic execution &
          |                           translation (expects one of { 0, 8, 16, 32, 64 })
          |    --c                  File/directory path for C translation (expects a path)
          |    --compare            Compare well-formed input program files (sans
          |                           contracts/prints)
          |-l, --last               Check last program only
          |    --run                Run input program file(s)
          |    --sat                Enable sat checking of facts and contracts
          |-x, --symexe             Enable symbolic execution
          |-t, --timeout            Timeout for algebra and auto (in milliseconds)
          |                           (expects an integer; default is 1000)
          |
          |Symbol Conversion Options:
          |    --ascii              Convert to ASCII
          |    --unicode            Convert to Unicode""".render

    var formula: Option[String] = None[String]()
    var server: B = false
    var auto: B = false
    var bitwidth: Z = 0
    var c: Option[String] = None[String]()
    var compare: B = false
    var last: B = false
    var run: B = false
    var sat: B = false
    var symexe: B = false
    var timeout: Z = 1000
    var ascii: B = false
    var unicode: B = false
    var j = i
    var isOption = T
    while (j < args.size && isOption) {
      val arg = args(j)
      if (ops.StringOps(arg).first == '-') {
        if (args(j) == "-h" || args(j) == "--help") {
          println(help)
          return Some(HelpOption())
        } else if (arg == "-f" || arg == "--formula") {
           val o: Option[Option[String]] = parseString(args, j + 1)
           o match {
             case Some(v) => formula = v
             case _ => return None()
           }
         } else if (arg == "--server") {
           val o: Option[B] = { j = j - 1; Some(!server) }
           o match {
             case Some(v) => server = v
             case _ => return None()
           }
         } else if (arg == "-a" || arg == "--auto") {
           val o: Option[B] = { j = j - 1; Some(!auto) }
           o match {
             case Some(v) => auto = v
             case _ => return None()
           }
         } else if (arg == "-b" || arg == "--bitwidth") {
           val o: Option[Z] = parseNumChoice(args, j + 1, ISZ(z"0", z"8", z"16", z"32", z"64"))
           o match {
             case Some(v) => bitwidth = v
             case _ => return None()
           }
         } else if (arg == "--c") {
           val o: Option[Option[String]] = parsePath(args, j + 1)
           o match {
             case Some(v) => c = v
             case _ => return None()
           }
         } else if (arg == "--compare") {
           val o: Option[B] = { j = j - 1; Some(!compare) }
           o match {
             case Some(v) => compare = v
             case _ => return None()
           }
         } else if (arg == "-l" || arg == "--last") {
           val o: Option[B] = { j = j - 1; Some(!last) }
           o match {
             case Some(v) => last = v
             case _ => return None()
           }
         } else if (arg == "--run") {
           val o: Option[B] = { j = j - 1; Some(!run) }
           o match {
             case Some(v) => run = v
             case _ => return None()
           }
         } else if (arg == "--sat") {
           val o: Option[B] = { j = j - 1; Some(!sat) }
           o match {
             case Some(v) => sat = v
             case _ => return None()
           }
         } else if (arg == "-x" || arg == "--symexe") {
           val o: Option[B] = { j = j - 1; Some(!symexe) }
           o match {
             case Some(v) => symexe = v
             case _ => return None()
           }
         } else if (arg == "-t" || arg == "--timeout") {
           val o: Option[Z] = parseNum(args, j + 1, Some(1000), None())
           o match {
             case Some(v) => timeout = v
             case _ => return None()
           }
         } else if (arg == "--ascii") {
           val o: Option[B] = { j = j - 1; Some(!ascii) }
           o match {
             case Some(v) => ascii = v
             case _ => return None()
           }
         } else if (arg == "--unicode") {
           val o: Option[B] = { j = j - 1; Some(!unicode) }
           o match {
             case Some(v) => unicode = v
             case _ => return None()
           }
         } else {
          eprintln(s"Unrecognized option '$arg'.")
          return None()
        }
        j = j + 2
      } else {
        isOption = F
      }
    }
    return Some(LogikaOption(help, parseArguments(args, j), formula, server, auto, bitwidth, c, compare, last, run, sat, symexe, timeout, ascii, unicode))
  }

  def parseX(args: ISZ[String], i: Z): Option[SireumTopOption] = {
    if (i >= args.size) {
      println(
        st"""Sireum Experimental Tools
            |
            |Available modes:
            |aadl                     AADL Tools
            |slang                    Slang toolbox""".render
      )
      return Some(HelpOption())
    }
    val opt = select("x", args, i, ISZ("aadl", "slang"))
    opt match {
      case Some(string"aadl") => parseAadl(args, i + 1)
      case Some(string"slang") => parseSlang(args, i + 1)
      case _ => return None()
    }
  }

  def parseAadl(args: ISZ[String], i: Z): Option[SireumTopOption] = {
    if (i >= args.size) {
      println(
        st"""Sireum AADL Tools
            |
            |Available modes:
            |awas                     Generate Awas from AADL model""".render
      )
      return Some(HelpOption())
    }
    val opt = select("aadl", args, i, ISZ("awas"))
    opt match {
      case Some(string"awas") => parseAwas(args, i + 1)
      case _ => return None()
    }
  }

  def parseAwas(args: ISZ[String], i: Z): Option[SireumTopOption] = {
    val help =
      st"""Awas generator
          |
          |Usage: ??
          |
          |Available Options:
          |-h, --help               Display this information""".render

    val j = i
    var isOption = T
    while (j < args.size && isOption) {
      val arg = args(j)
      if (ops.StringOps(arg).first == '-') {
        if (args(j) == "-h" || args(j) == "--help") {
          println(help)
          return Some(HelpOption())
        } else {
          eprintln(s"Unrecognized option '$arg'.")
          return None()
        }

      } else {
        isOption = F
      }
    }
    return Some(AwasOption(help, parseArguments(args, j)))
  }

  def parseSlang(args: ISZ[String], i: Z): Option[SireumTopOption] = {
    if (i >= args.size) {
      println(
        st"""The Sireum Language (Slang) Toolbox
            |
            |Available modes:
            |tipe                     Slang type checker""".render
      )
      return Some(HelpOption())
    }
    val opt = select("slang", args, i, ISZ("tipe"))
    opt match {
      case Some(string"tipe") => parseSlangTipe(args, i + 1)
      case _ => return None()
    }
  }

  def parseSlangTipe(args: ISZ[String], i: Z): Option[SireumTopOption] = {
    val help =
      st"""Slang Type Checker
          |
          |Usage: <option>* [<slang-file>]
          |
          |Available Options:
          |-s, --sourcepath         Sourcepath of Slang .scala files (expects path
          |                           strings)
          |-o, --outline            Perform type outlining only for files in the
          |                           sourcepath
          |-f, --force              Fully qualified names of traits, classes, and objects
          |                           to force full type checking on when type outlining
          |                           is enabled (expects a string separated by ",")
          |    --verbose            Enable verbose mode
          |-r, --no-runtime         Do not use built-in runtime (use runtime in
          |                           sourcepath)
          |-h, --help               Display this information
          |
          |Persistence Options:
          |    --save               Path to save type information to (outline should not
          |                           be enabled) (expects a path)
          |    --load               Path to load type information from (expects a path)
          |-z, --no-gzip            Disable gzip compression when saving and/or loading""".render

    var sourcepath: ISZ[String] = ISZ[String]()
    var outline: B = false
    var force: ISZ[String] = ISZ[String]()
    var verbose: B = false
    var noRuntime: B = false
    var save: Option[String] = None[String]()
    var load: Option[String] = None[String]()
    var gzip: B = true
    var j = i
    var isOption = T
    while (j < args.size && isOption) {
      val arg = args(j)
      if (ops.StringOps(arg).first == '-') {
        if (args(j) == "-h" || args(j) == "--help") {
          println(help)
          return Some(HelpOption())
        } else if (arg == "-s" || arg == "--sourcepath") {
           val o: Option[ISZ[String]] = parsePaths(args, j + 1)
           o match {
             case Some(v) => sourcepath = v
             case _ => return None()
           }
         } else if (arg == "-o" || arg == "--outline") {
           val o: Option[B] = { j = j - 1; Some(!outline) }
           o match {
             case Some(v) => outline = v
             case _ => return None()
           }
         } else if (arg == "-f" || arg == "--force") {
           val o: Option[ISZ[String]] = parseStrings(args, j + 1, ',')
           o match {
             case Some(v) => force = v
             case _ => return None()
           }
         } else if (arg == "--verbose") {
           val o: Option[B] = { j = j - 1; Some(!verbose) }
           o match {
             case Some(v) => verbose = v
             case _ => return None()
           }
         } else if (arg == "-r" || arg == "--no-runtime") {
           val o: Option[B] = { j = j - 1; Some(!noRuntime) }
           o match {
             case Some(v) => noRuntime = v
             case _ => return None()
           }
         } else if (arg == "--save") {
           val o: Option[Option[String]] = parsePath(args, j + 1)
           o match {
             case Some(v) => save = v
             case _ => return None()
           }
         } else if (arg == "--load") {
           val o: Option[Option[String]] = parsePath(args, j + 1)
           o match {
             case Some(v) => load = v
             case _ => return None()
           }
         } else if (arg == "-z" || arg == "--no-gzip") {
           val o: Option[B] = { j = j - 1; Some(!gzip) }
           o match {
             case Some(v) => gzip = v
             case _ => return None()
           }
         } else {
          eprintln(s"Unrecognized option '$arg'.")
          return None()
        }
        j = j + 2
      } else {
        isOption = F
      }
    }
    return Some(SlangTipeOption(help, parseArguments(args, j), sourcepath, outline, force, verbose, noRuntime, save, load, gzip))
  }

  def parseArguments(args: ISZ[String], i: Z): ISZ[String] = {
    var r = ISZ[String]()
    var j = i
    while (j < args.size) {
      r = r :+ args(j)
      j = j + 1
    }
    return r
  }

  def parsePaths(args: ISZ[String], i: Z): Option[ISZ[String]] = {
    return tokenize(args, i, "path", pathSep, F)
  }

  def parsePath(args: ISZ[String], i: Z): Option[Option[String]] = {
    if (i >= args.size) {
      eprintln("Expecting a path, but none found.")
    }
    return Some(Some(args(i)))
  }

  def parseStrings(args: ISZ[String], i: Z, sep: C): Option[ISZ[String]] = {
    tokenize(args, i, "string", sep, F) match {
      case r@Some(_) => return r
      case _ => return None()
    }
  }

  def parseString(args: ISZ[String], i: Z): Option[Option[String]] = {
    if (i >= args.size) {
      eprintln("Expecting a string, but none found.")
      return None()
    }
    return Some(Some(args(i)))
  }

  def parseNums(args: ISZ[String], i: Z, sep: C, minOpt: Option[Z], maxOpt: Option[Z]): Option[ISZ[Z]] = {
    tokenize(args, i, "integer", sep, T) match {
      case Some(sargs) =>
        var r = ISZ[Z]()
        for (arg <- sargs) {
          parseNumH(arg, minOpt, maxOpt) match {
            case Some(n) => r = r :+ n
            case _ => return None()
          }
        }
        return Some(r)
      case _ => return None()
    }
  }

  def tokenize(args: ISZ[String], i: Z, tpe: String, sep: C, removeWhitespace: B): Option[ISZ[String]] = {
    if (i >= args.size) {
      eprintln(s"Expecting a sequence of $tpe separated by '$sep', but none found.")
      return None()
    }
    val arg = args(i)
    return Some(tokenizeH(arg, sep, removeWhitespace))
  }

  def tokenizeH(arg: String, sep: C, removeWhitespace: B): ISZ[String] = {
    val argCis = conversions.String.toCis(arg)
    var r = ISZ[String]()
    var cis = ISZ[C]()
    var j = 0
    while (j < argCis.size) {
      val c = argCis(j)
      if (c == sep) {
        r = r :+ conversions.String.fromCis(cis)
        cis = ISZ[C]()
      } else {
        val allowed: B = c match {
          case c"\n" => !removeWhitespace
          case c" " => !removeWhitespace
          case c"\r" => !removeWhitespace
          case c"\t" => !removeWhitespace
          case _ => T
        }
        if (allowed) {
          cis = cis :+ c
        }
      }
      j = j + 1
    }
    if (cis.size > 0) {
      r = r :+ conversions.String.fromCis(cis)
    }
    return r
  }

  def parseNumChoice(args: ISZ[String], i: Z, choices: ISZ[Z]): Option[Z] = {
    val set = HashSet.empty[Z] ++ choices
    parseNum(args, i, None(), None()) match {
      case r@Some(n) =>
        if (set.contains(n)) {
          return r
        } else {
          eprintln(s"Expecting one of the following: $set, but found $n.")
          return None()
        }
      case r => return r
    }
  }

  def parseNum(args: ISZ[String], i: Z, minOpt: Option[Z], maxOpt: Option[Z]): Option[Z] = {
    if (i >= args.size) {
      eprintln(s"Expecting an integer, but none found.")
      return None()
    }
    return parseNumH(args(i), minOpt, maxOpt)
  }

  def parseNumH(arg: String, minOpt: Option[Z], maxOpt: Option[Z]): Option[Z] = {
    Z(arg) match {
      case Some(n) =>
        minOpt match {
          case Some(min) =>
            if (n < min) {
              eprintln(s"Expecting an integer at least $min, but found $n.")
              return None()
            }
          case _ =>
        }
        maxOpt match {
          case Some(max) =>
            if (n > max) {
              eprintln(s"Expecting an integer at most $max, but found $n.")
              return None()
            }
            return Some(n)
          case _ =>
        }
        return Some(n)
      case _ =>
        eprintln(s"Expecting an integer, but found '$arg'.")
        return None()
    }
  }

  def select(mode: String, args: ISZ[String], i: Z, choices: ISZ[String]): Option[String] = {
    val arg = args(i)
    var cs = ISZ[String]()
    for (c <- choices) {
      if (ops.StringOps(c).startsWith(arg)) {
        cs = cs :+ c
      }
    }
    cs.size match {
      case z"0" =>
        eprintln(s"$arg is not a mode of $mode.")
        return None()
      case z"1" => return Some(cs(0))
      case _ =>
        eprintln(
          st"""Which one of the following modes did you mean by '$arg'?
              |${(cs, "\n")}""".render)
        return None()
    }
  }
}