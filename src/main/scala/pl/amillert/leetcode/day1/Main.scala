package pl.amillert
package leetcode
package day1

object Main extends App {
  def binary_search(
      nums: Array[Int],
      target: Int,
      l: Int,
      r: Int
    ): Int = {
    val pivot = (l + r) / 2
    val curr  = nums(pivot)

    if (curr == target) pivot
    else if (curr < target) binary_search(nums, target, pivot + 1, r)
    else binary_search(nums, target, l, pivot - 1)
  }

  // 35. Search Insert Position
  def search_insert(nums: Array[Int], target: Int): Int = {
    def go(
        l: Int,
        r: Int
      ): Int =
      if (l > r) l
      else {
        val pivot = (l + r) / 2
        val curr  = nums(pivot)

        if (curr == target) pivot
        else if (curr < target) go(pivot + 1, r)
        else go(l, pivot - 1)
      }

    if (target > nums.last) nums.size
    else if (target < nums.head) 0
    else go(0, nums.size)
  }

  // 961. N-Repeated Element in Size 2N Array
  def repeatedNTimes(nums: Array[Int]): Int = {
    val n = nums.size / 2
    nums
      .groupBy(identity)
      .filter(_._2.size == n)
      .head
      ._1
  }

  // 88. Merge Sorted Array (but return w/ no mutations)
  def merge1(
      nums1: Array[Int],
      m: Int,
      nums2: Array[Int],
      n: Int
    ): Array[Int] = {
    def go(
        nums1: Array[Int],
        nums2: Array[Int],
        res: Array[Int]
      ): Array[Int] =
      if (nums1.isEmpty && nums2.isEmpty) res
      else if (nums1.isEmpty) res ++ nums2
      else if (nums2.isEmpty) res ++ nums1
      else {
        val h1 = nums1.head
        val h2 = nums2.head

        if (h1 <= h2) go(nums1.tail, nums2, res :+ h1)
        else go(nums1, nums2.tail, res :+ h2)
      }
    if (m == 0) nums2
    else if (n == 0) nums1
    else go(nums1.take(m), nums2.take(m), Array.empty[Int])
  }

  def merge2(
      nums1: Array[Int],
      m: Int,
      nums2: Array[Int],
      n: Int
    ): Array[Int] = {
    def go(
        nums1: List[Int],
        nums2: List[Int],
        res: List[Int]
      ): List[Int] =
      nums1 -> nums2 match {
        case Nil -> Nil => res
        case n1 -> Nil  => res ++ n1
        case Nil -> n2  => res ++ n2
        case (h1 :: t1) -> (h2 :: t2) =>
          if (h1 <= h2) go(t1, h2 :: t2, res :+ h1)
          else go(h1 :: t1, t2, res :+ h2)
      }

    if (m == 0) nums2
    else if (n == 0) nums1
    else go(nums1.take(m).toList, nums2.take(m).toList, List.empty[Int]).toArray
  }

  // 58. Length of Last Word
  def lengthOfLastWord(s: String): Int =
    s.split(' ').last.size

  // 83. Remove Duplicates from Sorted List
  sealed trait MyList
  object MyList {
    case class ListNode(x: Int = 0, next: MyList) extends MyList
    case object EmptyList                         extends MyList
  }

  import MyList._

  def deleteDuplicates(list: MyList): MyList = list match {
    case EmptyList              => EmptyList
    case ListNode(h, EmptyList) => ListNode(h, EmptyList)
    case ListNode(h, tail @ ListNode(hh, t)) if h == hh =>
      deleteDuplicates(tail)
    case ListNode(h, tail @ ListNode(hh, t)) =>
      ListNode(h, deleteDuplicates(tail))
  }

  sealed trait Tree
  object Tree {
    case class TreeNode(
        value: Int,
        left: Tree = EmptyNode,
        right: Tree = EmptyNode
      )                   extends Tree
    case object EmptyNode extends Tree
  }

  import Tree._

  def inorder(t: Tree): Seq[Option[Int]] = t match {
    case EmptyNode                         => Seq(None)
    case TreeNode(x, EmptyNode, EmptyNode) => Seq(Some(x))
    case TreeNode(x, l, r)                 => inorder(l) :+ Some(x) :++ inorder(r)
  }

  // 100. Same Tree
  def isSameTree(p: Tree, q: Tree): Boolean =
    inorder(p) == inorder(q)
  // def isSameTree(p: Tree, q: Tree): Boolean = {
  //   val pp = inorder(p)
  //   val qq = inorder(q)
  //   println(pp)
  //   println(qq)
  //   println()
  //   pp == qq
  // }

  // 94. Binary Tree Inorder Traversal
  def inorderTraversal(t: Tree): Seq[Int] = t match {
    case EmptyNode                         => Seq.empty[Int]
    case TreeNode(x, EmptyNode, EmptyNode) => Seq(x)
    case TreeNode(x, l, r)                 => inorderTraversal(l) :+ x :++ inorderTraversal(r)
  }

  assert(binary_search(Array(1, 2, 3, 4, 7, 12, 14), 7, 0, 7) == 4)
  assert(search_insert(Array(1, 2, 3, 4, 7, 12, 14), 8) == 5)
  assert(repeatedNTimes(Array(1, 2, 3, 3)) == 3)
  assert(repeatedNTimes(Array(2, 1, 2, 5, 3, 2)) == 2)
  assert(repeatedNTimes(Array(5, 1, 5, 2, 5, 3, 5, 4)) == 5)
  // println(merge2(Array(1,2,3,0,0,0), 3, Array(2,5,6), 3).mkString(",") + "\t" + Array(1,2,2,3,5,6).mkString(","))
  // println(merge2(Array(1), 1, Array.empty[Int], 0).mkString(",") + "\t" + Array(1).mkString(","))
  // println(merge2(Array(0), 0, Array(1), 1).mkString(",") + "\t" + Array(1).mkString(","))
  assert(
    merge1(Array(1, 2, 3, 0, 0, 0), 3, Array(2, 5, 6), 3)
      .mkString(",")
      .hashCode == Array(1, 2, 2, 3, 5, 6).mkString(",").hashCode
  )
  assert(
    merge1(Array(1), 1, Array.empty[Int], 0).mkString(",").hashCode == Array(1)
      .mkString(",")
      .hashCode
  )
  assert(merge1(Array(0), 0, Array(1), 1).mkString(",").hashCode == Array(1).mkString(",").hashCode)
  assert(
    merge2(Array(1, 2, 3, 0, 0, 0), 3, Array(2, 5, 6), 3)
      .mkString(",")
      .hashCode == Array(1, 2, 2, 3, 5, 6).mkString(",").hashCode
  )
  assert(
    merge2(Array(1), 1, Array.empty[Int], 0).mkString(",").hashCode == Array(1)
      .mkString(",")
      .hashCode
  )
  assert(merge2(Array(0), 0, Array(1), 1).mkString(",").hashCode == Array(1).mkString(",").hashCode)
  assert(lengthOfLastWord("jakiś tam     sobie napis tu") == 2)
  assert(
    deleteDuplicates(
      ListNode(1, ListNode(1, ListNode(2, ListNode(3, ListNode(3, EmptyList)))))
    ) == ListNode(1, ListNode(2, ListNode(3, EmptyList)))
  )

  assert(
    isSameTree(TreeNode(1, TreeNode(2), TreeNode(3)), TreeNode(1, TreeNode(2), TreeNode(3))) == true
  )
  assert(
    isSameTree(TreeNode(1, TreeNode(2), EmptyNode), TreeNode(1, EmptyNode, TreeNode(2))) == false
  )
  assert(
    isSameTree(
      TreeNode(1, TreeNode(2), TreeNode(1)),
      TreeNode(1, TreeNode(1), TreeNode(2))
    ) == false
  )
  assert(
    inorderTraversal(TreeNode(1, EmptyNode, TreeNode(2, TreeNode(3), EmptyNode))) == Seq(1, 3, 2)
  )
  assert(inorderTraversal(TreeNode(1, TreeNode(2))) == Seq(2, 1))
  assert(inorderTraversal(TreeNode(1, EmptyNode, TreeNode(2))) == Seq(1, 2))
}
