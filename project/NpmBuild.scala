import play.sbt.PlayRunHook
import java.nio.file.{Files, StandardOpenOption}

import sbt._

import scala.sys.process.Process

/*
https://developers.cyberagent.co.jp/blog/archives/25126/
 */
object NpmBuild {

  val isWindows = System.getProperty("os.name").toLowerCase().contains("win")

  object Commands {
    val install = (if (isWindows) "cmd /c " else "") + "npm install"
    val build = (if (isWindows) "cmd /c " else "") + "npm run build"
    val clean = (if (isWindows) "cmd /c " else "") + "npm run clean"
    val buildWatch = (if (isWindows) "cmd /c " else "") + "npm run build watch"
  }

  object Shell {
    def execute(cmd: String, cwd: File, envs: (String, String)*): Int = {
      Process(cmd, cwd, envs: _*).!
    }

    def executeWithMsg(cmd: String, cwd: File, envs: (String, String)*): String = {
      Process(cmd, cwd, envs: _*).!!
    }

    def invoke(cmd: String, cwd: File, envs: (String, String)*): Process = {
      Process(cmd, cwd, envs: _*).run
    }
  }

  def apply(base: File): PlayRunHook = {

    val frontendBase = base / "frontend"
    val packageJsonPath = frontendBase / "package.json"

    val frontEndTarget = base / "target" / "frontend"
    val packageJsonHashPath = frontEndTarget / "package.json.hash"

    object FrontendBuildProcess extends PlayRunHook {

      var process: Option[Process] = None

      override def beforeStarted(): Unit = {
        println("*N* before started")

        val currPackageJsonHash = Files.readString(packageJsonPath.toPath).hashCode().toString()
        val oldPackageJsonHash = getStoredPackageJsonHash()

        if (!oldPackageJsonHash.exists(_ == currPackageJsonHash)) {
          println(s"Found new/changed package.json. Run '${Commands.install}'...")

          Shell.execute(Commands.install, frontendBase)

          updateStoredPackageJsonHash(currPackageJsonHash)
        }
      }

      override def afterStarted(): Unit = {
        println("*N* after started")
        Shell.execute(Commands.clean, frontendBase)
        process = Option(Shell.invoke(Commands.buildWatch, frontendBase))
      }

      override def afterStopped(): Unit = {
        println("*N* after stopped")
        process.foreach(_.destroy)
        process = None
      }

      private def getStoredPackageJsonHash(): Option[String] = {
        if (packageJsonHashPath.exists()) {
          val hash = Files.readString(packageJsonHashPath.toPath)
          Some(hash)
        } else {
          None
        }
      }

      private def updateStoredPackageJsonHash(hash: String): Unit = {
        val dir = frontEndTarget

        if (!dir.exists) {
          dir.mkdirs
        }

        Files.writeString(packageJsonHashPath.toPath, hash)

      }
    }

    FrontendBuildProcess
  }
}