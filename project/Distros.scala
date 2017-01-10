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

import java.io.FileInputStream
import java.util.jar.JarInputStream

import ammonite.ops._

object Distros {
  val baseDir: Path = {
    Option(System.getenv("SIREUM_DISTRO_BUILD_DIR")) match {
      case Some(v) => Path(new java.io.File(v))
      case _ => pwd
    }
  }
  val ideaDir: Path = baseDir / 'distros / 'idea

  implicit val wd: Path = baseDir
  lazy val VER: String = {
    %%('git, 'log, "-n", "1", "--pretty=format:%H")(pwd).out.lines.head.trim
  }

  val ideaVer = "2016.3.2"

  val ideaExtMap = Map(
    "mac" -> ".dmg",
    "win" -> ".exe",
    "linux" -> ".tar.gz"
  )

  val jarPlugins = Map(
    "rst" -> "rest.jar",
    "batch" -> "idea-batch.jar",
    "latex" -> "idea-latex.jar"
  )

  val pluginUpdateIdMap = Map(
    "sireum" -> 31441,
    "jdt" -> 31124,
    "scala" -> 31659,
    "sbt" -> 22670,
    "markdown" -> 30117,
    "snakeyaml" -> 24503,
    "antlr" -> 31133,
    "asm" -> 18619,
    "bash" -> 31391,
    "cmd" -> 18875,
    "batch" -> 22567,
    "compare" -> 24991,
    "latex" -> 18476,
    "python" -> 30456,
    "rst" -> 14700,
    "ignore" -> 30395
  )

  val hasExes = (baseDir / 'distros / "idea.exe").toIO.isFile &&
    (baseDir / 'distros / "idea64.exe").toIO.isFile

  def buildIdea(): Unit = {
    if (!(baseDir / 'distros / "sireum-v3-VER").toIO.exists) {
      sys.error("Need to run distros task first.")
      return
    }

    try {
      %%("7z", "-h")
    } catch {
      case _: Throwable => sys.error("Need 7z.")
    }

    val isMac = System.getProperty("os.name").toLowerCase.contains("mac")
    val hasWine = try {
      %%('wineconsole, "-h"); true
    } catch {
      case _: Exception => false
    }

    downloadResourceHacker()
    downloadPlugins()
    if (isMac) buildIdea("mac")
    else println("Idea mac64 distro will not be built because it can only be built on a macOS host.")
    if (hasWine || hasExes) buildIdea("win")
    else println("Idea win64 distro will not be built because of missing wineconsole.")
    buildIdea("linux")

    //rm ! baseDir / 'distros / 'idea
  }

  def downloadResourceHacker(): Unit = {
    val distroPath = pwd / 'resources / 'distro
    val rhDir = distroPath / 'resource_hacker
    if (!(rhDir / "ResourceHacker.exe").toIO.exists) {
      val rhFilename = "resource_hacker.zip"
      rm ! rhDir
      rm ! distroPath / rhFilename
      val url = s"http://www.angusj.com/resourcehacker/$rhFilename"
      print(s"Downloading $url ... ")
      %%('wget, "-q", url)(distroPath)
      println("done!")
      print(s"Extracting $rhFilename ... ")
      mkdir ! rhDir
      %%('unzip, "-qo", s"../$rhFilename")(rhDir)
      rm ! distroPath / rhFilename
      println("done!")
    }
  }

  def downloadPlugins(): Unit = {
    val pluginsDir = ideaDir / 'plugins
    mkdir ! pluginsDir
    for ((name, updateId) <- pluginUpdateIdMap if !(pluginsDir / s"$name-$updateId.zip").toIO.exists) {
      val url = s"https://plugins.jetbrains.com/plugin/download?pr=idea&updateId=$updateId"
      print(s"Downloading $name plugin from $url ... ")
      %%('wget, "-q", "-O", s"$name-$updateId.zip", url)(pluginsDir)
      println("done!")
    }
  }

  def extractPlugins(p: Path): Unit = {
    println("Downloading idea plugins ...")
    for ((name, updateId) <- pluginUpdateIdMap) {
      jarPlugins.get(name) match {
        case Some(jarName) =>
          print(s"Copying $name plugin ... ")
          %%('cp, ideaDir / 'plugins / s"$name-$updateId.zip", p / jarPlugins(name))(p)
        case _ =>
          print(s"Extracting $name plugin ... ")
          %%('unzip, "-oq", ideaDir / 'plugins / s"$name-$updateId.zip")(p)
      }
      println("done!")
    }
  }

  def patchIdeaProperties(platform: String, p: Path): Unit = {
    print(s"Patching $p ... ")
    val content = read ! p
    val newContent = platform match {
      case "mac" =>
        val i = content.indexOf("idea.paths.selector")
        val j = content.indexOf("<string>", i)
        val k = content.indexOf("</string>", j)
        content.substring(0, j) + s"<string>Sireum$ideaVer" + content.substring(k)
      case "win" =>
        s"idea.config.path=$${user.home}/.Sireum$ideaVer/config\r\nidea.system.path=$${user.home}/.Sireum$ideaVer/system\r\n" + content
      case "linux" =>
        s"idea.config.path=$${user.home}/.Sireum$ideaVer/config\nidea.system.path=$${user.home}/.Sireum$ideaVer/system\n" + content
    }
    rm ! p
    write(p, newContent)
    println("done!")
  }

  def patchVMOptions(platform: String, p: Path): Unit = {
    print(s"Patching $p ... ")
    val content = read ! p
    val newContent = platform match {
      case "win" => s"${content.trim}\r\n-Dorg.sireum.ive=Sireum$ideaVer\r\n-Dfile.encoding=UTF-8\r\n"
      case _ => s"${content.trim}\n-Dorg.sireum.ive=Sireum$ideaVer\n-Dfile.encoding=UTF-8\n"
    }
    rm ! p
    write(p, newContent)
    println("done!")
  }

  def patchImages(path: Path): Unit = {
    val filePath = path / 'lib / "resources.jar"
    print(s"Patching $filePath ... ")
    %%('zip, filePath,
      "idea_community_about.png",
      "idea_community_about@2x.png",
      "idea_community_logo.png",
      "idea_community_logo@2x.png")(pwd / 'resources / 'distro / 'images)
    println("done!")
  }

  def patchIconExe(filePath: Path): Unit = {
    if (hasExes) {
      print(s"Replacing $filePath ... ")
      %%('cp, pwd / 'distros / filePath.last, filePath)
      println("done!")
    } else {
      def toWinePath(p: Path) = "Z:" + p.toString.replaceAllLiterally("/", "\\")

      val wineFilePath = toWinePath(filePath)
      val icoFilePath = toWinePath(pwd / 'resources / 'distro / 'icons / "idea.ico")
      print(s"Patching $filePath ... ")
      %%('wineconsole, pwd / 'resources / 'distro / 'resource_hacker / "ResourceHacker.exe",
        "-open", wineFilePath, "-save", wineFilePath, "-action", 'addoverwrite,
        "-res", icoFilePath, "-mask", "ICONGROUP,107,")
      println("done!")
    }
  }

  def patchIcon(platform: String, path: Path): Unit = {
    val iconsPath = pwd / 'resources / 'distro / 'icons
    val (dirPath, filename) = platform match {
      case "mac" => (path / 'Resources, "idea.icns")
      case "win" =>
        patchIconExe(path / 'bin / "idea.exe")
        patchIconExe(path / 'bin / "idea64.exe")
        (path / 'bin, "idea.ico")
      case "linux" => (path / 'bin, "idea.png")
    }
    print(s"Replacing icon $dirPath/$filename ... ")
    %%('cp, filename, dirPath)(iconsPath)
    println("done!")
    val iconsJar = path / 'lib / "icons.jar"
    print(s"Patching $iconsJar ... ")
    val jis = new JarInputStream(new FileInputStream(iconsJar.toIO))
    var entries = Set[String]()
    var done = false
    do {
      Option(jis.getNextEntry) match {
        case Some(e) if !e.isDirectory => entries += e.getName
        case None => done = true
        case _ =>
      }
    } while (!done)
    val entriesToUpdate =
      (for (f <- iconsPath.toIO.listFiles if f.getName != "idea.icns" && f.getName != "idea.png") yield {
        require(entries.contains(f.getName), s"File ${f.getName} is not in $iconsJar.")
        f.getName
      }).toVector
    val cmd = "zip" +: iconsJar.toString +: entriesToUpdate
    Shellout.executeStream(iconsPath, Command(cmd, Map(), Shellout.executeStream))
    println("done!")
  }

  def buildIdea(platform: String): Unit = {
    println(s"Building Sireum v3 idea ${platform}64 distro ...")
    val url = s"https://download.jetbrains.com/idea/ideaIC-$ideaVer${ideaExtMap(platform)}"
    val filename = url.substring(url.lastIndexOf('/') + 1)
    val buildDir = ideaDir
    %%('mkdir, "-p", buildDir)
    val file = buildDir / filename
    if (!file.toIO.exists) {
      print(s"Downloading from $url ... ")
      %%('wget, "-q", url)(buildDir)
      println("done!")
    }
    rm ! buildDir / platform
    print(s"Extracting $file ... ")
    platform match {
      case "mac" =>
        val tempDir = buildDir / platform
        mkdir ! tempDir
        %%("hdiutil", "attach", file)
        %%('cp, "-R", root / 'Volumes / "IntelliJ IDEA CE" / "IntelliJ IDEA CE.app", tempDir / "Sireum.app")
        %%("hdiutil", "eject", root / 'Volumes / "IntelliJ IDEA CE")
        println("done!")
        extractPlugins(tempDir / "Sireum.app" / 'Contents / 'plugins)
        patchIdeaProperties(platform, tempDir / "Sireum.app" / 'Contents / "Info.plist")
        patchVMOptions(platform, tempDir / "Sireum.app" / 'Contents / 'bin / "idea.vmoptions")
        patchImages(tempDir / "Sireum.app" / 'Contents)
        patchIcon(platform, tempDir / "Sireum.app" / 'Contents)
      case "win" =>
        val tempDir = buildDir / platform / "sireum-v3" / 'apps / 'idea
        mkdir ! tempDir
        %%("7z", "x", file)(tempDir)
        rm ! tempDir / '$PLUGINSDIR
        println("done!")
        extractPlugins(tempDir / 'plugins)
        patchIdeaProperties(platform, tempDir / 'bin / "idea.properties")
        patchVMOptions(platform, tempDir / 'bin / "idea.exe.vmoptions")
        patchVMOptions(platform, tempDir / 'bin / "idea64.exe.vmoptions")
        patchImages(tempDir)
        patchIcon(platform, tempDir)
        %%('cp, "-p", pwd / 'resources / 'distro / "idea.bat", buildDir / platform / "sireum-v3")
        %%('cp, "-p", pwd / 'resources / 'distro / "idea64.bat", buildDir / platform / "sireum-v3")
      case "linux" =>
        val tempDir = buildDir / platform / "sireum-v3" / 'apps
        mkdir ! tempDir
        %%("tar", "xf", file)(tempDir)
        mv(tempDir / tempDir.toIO.listFiles()(0).getName, tempDir / 'idea)
        println("done!")
        extractPlugins(tempDir / 'idea / 'plugins)
        patchIdeaProperties(platform, tempDir / 'idea / 'bin / "idea.properties")
        patchVMOptions(platform, tempDir / 'idea / 'bin / "idea.vmoptions")
        patchVMOptions(platform, tempDir / 'idea / 'bin / "idea64.vmoptions")
        patchImages(tempDir / 'idea)
        patchIcon(platform, tempDir / 'idea)
        %%('cp, "-p", pwd / 'resources / 'distro / 'idea, buildDir / platform / "sireum-v3")
        %%('chmod, "+x", buildDir / platform / "sireum-v3" / 'idea)
    }
    print("Extracting Sireum v3 distro ... ")
    platform match {
      case "mac" =>
        %%('unzip, "-oq", baseDir / 'distros / s"sireum-v3-${platform}64.zip")(baseDir / 'distros / 'idea / platform / "Sireum.app" / "Contents" / "Resources")
      case _ =>
        %%('unzip, "-oq", baseDir / 'distros / s"sireum-v3-${platform}64.zip")(baseDir / 'distros / 'idea / platform)
        mv(ideaDir / platform / "sireum-v3", ideaDir / platform / "Sireum")
    }
    println("done!")
    print(s"Packaging ${platform}64 idea distro ... ")
    platform match {
      case "mac" =>
        val bundle = baseDir / 'distros / "sireum-v3-idea-mac64.tar.gz"
        val bundleDmg = baseDir / 'distros / "sireum-v3-idea-mac64.dmg"
        rm ! bundle
        rm ! bundleDmg
        %%('tar, 'cfz, bundle, "Sireum.app")(ideaDir / platform)
        val ver = (read ! baseDir / 'distros / "sireum-v3-VER").substring(0, 7)
        val app = ideaDir / platform / "Sireum.app"
        val background = pwd / 'resources / 'distro / 'images / "dmg-background.png"
        %%('dmgbuild, "-s", "settings.py", "-D", s"app=$app",
          "-D", s"background=$background", s"Sireum v3 $ver", bundleDmg)(baseDir / 'distros)
        rm ! ideaDir / platform
      case "linux" =>
        val bundle = baseDir / 'distros / "sireum-v3-idea-linux64.zip"
        rm ! bundle
        %%('zip, "-r", bundle, "Sireum")(ideaDir / platform)
        rm ! ideaDir / platform
      case "win" =>
        val bundle = baseDir / 'distros / "sireum-v3-idea-win64.zip"
        rm ! bundle
        %%('zip, "-r", bundle, "Sireum")(ideaDir / platform)
        rm ! ideaDir / platform
    }
    println("done!")
  }

  def build(): Unit = {
    rm ! baseDir / 'distros / "sireum-v3-mac64.zip"
    rm ! baseDir / 'distros / "sireum-v3-win64.zip"
    rm ! baseDir / 'distros / "sireum-v3-linux64.zip"
    rm ! baseDir / 'distros / "sireum-v3-VER"
    write(baseDir / 'distros / "sireum-v3-VER", VER)
    build("mac")
    build("win")
    build("linux")
  }

  def build(platform: String): Unit = {
    println(s"Building distro for ${platform}64...")
    println()
    rm ! baseDir / 'distros / "sireum-v3"
    mkdir ! baseDir / 'distros / "sireum-v3" / 'bin
    write(baseDir / 'distros / "sireum-v3" / 'bin / 'VER, VER)
    val licenseLines = read.lines ! pwd / "license.txt"
    platform match {
      case "win" =>
        write(baseDir / 'distros / "sireum-v3" / "license.txt", licenseLines.mkString("\r\n"))
        cp(pwd / 'resources / 'distro / "sireum.bat", baseDir / 'distros / "sireum-v3" / "sireum.bat")
      case _ =>
        write(baseDir / 'distros / "sireum-v3" / "license.txt", licenseLines.mkString("\n"))
        cp(pwd / "sireum", baseDir / 'distros / "sireum-v3" / "sireum")
    }
    cp(pwd / 'bin / "sireum.jar", baseDir / 'distros / "sireum-v3" / 'bin / "sireum.jar")
    cp(pwd / 'bin / "prelude.sh", baseDir / 'distros / "sireum-v3" / 'bin / "prelude.sh")
    %('bash, baseDir / 'distros / "sireum-v3" / 'bin / "prelude.sh", PLATFORM = platform, DISTROS = "true")
    rm ! baseDir / 'distros / "sireum-v3" / 'bin / "prelude.sh"
    %('zip, "-qr", s"sireum-v3-${platform}64.zip", "sireum-v3")(baseDir / 'distros)
    rm ! baseDir / 'distros / "sireum-v3"
  }
}