package pl.amillert
package leetcode
package day2

import common.adt._
import common.adt.LinkedList._
import common.adt.Tree._

object Main extends App {
  // 101. Symmetric Tree
  def isSymmetric[A](root1: Tree[A], root2: Tree[A]): Boolean = root1 -> root2 match {
    case EmptyTree -> EmptyTree => true
    case TreeNode(x1, l1, r1) -> TreeNode(x2, l2, r2) =>
      x1 == x2 && isSymmetric(l1, r2) && isSymmetric(r1, l2)
    case _ => false
  }

  // 25. Reverse Nodes in k-Group
  def join[A](left: LinkedList[A], right: LinkedList[A]): LinkedList[A] = left match {
    case EmptyList      => right
    case ListNode(h, t) => ListNode(h, join(t, right))
  }

  def reverse[A](list: LinkedList[A], res: LinkedList[A]): LinkedList[A] = list match {
    case EmptyList       => res
    case ListNode(x, xs) => reverse(xs, ListNode(x, res))
  }

  def reverseKGroup(list: LinkedList[Int], original: Int): LinkedList[Int] = {
    def go(
        list: LinkedList[Int],
        tmp: LinkedList[Int],
        res: LinkedList[Int],
        k: Int
      ): LinkedList[Int] =
      if (original == 1) list
      else
        list match {
          case EmptyList => join(res, reverse(tmp, EmptyList))
          case ListNode(x, xs) =>
            if (k > 1) go(xs, ListNode(x, tmp), res, k - 1)
            else go(xs, EmptyList, join(res, ListNode(x, tmp)), original)
        }

    go(list, EmptyList, EmptyList, original)
  }

  // 23. Merge k Sorted Lists
  def toScalaList[A](xs: LinkedList[A], res: List[A] = List.empty[A]): List[A] = xs match {
    case EmptyList      => res
    case ListNode(h, t) => toScalaList(t, res :+ h)
  }

  def toMyList[A](xs: List[A], res: LinkedList[A] = EmptyList): LinkedList[A] = xs match {
    case Nil    => res
    case h :: t => toMyList(t, ListNode(h, res))
  }

  def mergeKLists(lists: Array[ListNode[Int]]) =
    toMyList(
      lists
        .flatMap(toScalaList(_))
        .sorted(Ordering.Int.reverse)
        .toList
    )

  val t1 = TreeNode(1, TreeNode(2, TreeNode(3), TreeNode(4)), TreeNode(2, TreeNode(3), TreeNode(4)))
  val t2 = TreeNode(1, TreeNode(2, TreeNode(3), TreeNode(4)), TreeNode(2, TreeNode(4), TreeNode(3)))
  val t3 = TreeNode(1, TreeNode(2, EmptyTree, TreeNode(3)), TreeNode(2, EmptyTree, TreeNode(4)))
  assert(isSymmetric(t1, t1) == false)
  assert(isSymmetric(t2, t2) == true)
  assert(isSymmetric(t3, t3) == false)

  val l1    = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5, EmptyList)))))
  val revL1 = ListNode(2, ListNode(1, ListNode(4, ListNode(3, ListNode(5, EmptyList)))))
  val revL2 = ListNode(3, ListNode(2, ListNode(1, ListNode(4, ListNode(5, EmptyList)))))
  assert(reverseKGroup(l1, 2) == revL1)
  assert(reverseKGroup(l1, 1) == l1)
  assert(reverseKGroup(l1, 3) == revL2)
  assert(reverseKGroup(ListNode(1), 1) == ListNode(1))

  val masterList = ListNode(
    1,
    ListNode(
      1,
      ListNode(
        1,
        ListNode(
          2,
          ListNode(
            2,
            ListNode(
              2,
              ListNode(
                3,
                ListNode(
                  3,
                  ListNode(
                    3,
                    ListNode(4, ListNode(4, ListNode(4, ListNode(5, ListNode(5, ListNode(5))))))
                  )
                )
              )
            )
          )
        )
      )
    )
  )
  assert(mergeKLists(Array(l1, revL1, revL2)) == masterList)
}
