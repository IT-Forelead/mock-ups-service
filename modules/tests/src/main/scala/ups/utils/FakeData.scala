package ups.utils

import scala.util.Random

object FakeData {
  def randomString(length: Int): String = Random.alphanumeric.take(length).mkString
}
