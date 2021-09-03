package pl.amillert
package leetcode
package day4

object Main extends App {
  // 49. Group Anagrams
  def groupAnagrams(strs: List[String]): List[List[String]] =
    strs.map(_.sorted).zipWithIndex.groupBy(_._1).toList.map {
      case sorted -> grouped =>
        grouped.map(pair => strs(pair._2))
    }

  // 121. Best Time to Buy and Sell Stock
  def maxProfitON2(prices: List[Int]): Int =
    ((for {
      x <- 0 to prices.size - 2
      y <- x + 1 to prices.size - 1
    } yield prices(y) - prices(x)).max :: 0 :: Nil).max

  def maxProfitON(prices: List[Int]): Int = ???

  // sorting should not matter since the problem specification says "return the answer in any order"
  assert(
    groupAnagrams(List("eat", "tea", "tan", "ate", "nat", "bat")).map(_.sorted)
      == List(List("bat"), List("nat", "tan"), List("ate", "eat", "tea")).map(_.sorted)
  )
  assert(groupAnagrams(List("a")) == List(List("a")))
  assert(groupAnagrams(List("")) == List(List("")))

  assert(maxProfitON2(List(7, 1, 5, 3, 6, 4)) == 5)
  assert(maxProfitON2(List(7, 6, 4, 3, 2, 1)) == 0)
}
