import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`, mill.playlib._

object hottocoffee extends PlayModule with SingleModule {

  def scalaVersion = "3.3.1"

  def playVersion = "3.0.0"

  def twirlVersion = "1.6.0-RC2"

  object test extends PlayTests
}
