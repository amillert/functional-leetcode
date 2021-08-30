package pl.amillert
package leetcode
package day2

import scala.collection.View
import scala.collection.immutable

object Main extends App {

  sealed trait Tree
  object Tree {
    case object EmptyNode extends Tree
    case class TreeNode(
        value: Int,
        left: Tree = EmptyNode,
        right: Tree = EmptyNode
      ) extends Tree
  }

  import Tree._

  // 101. Symmetric Tree
  def isSymmetric(root1: Tree, root2: Tree): Boolean = root1 -> root2 match {
    case EmptyNode -> EmptyNode => true
    case TreeNode(x1, l1, r1) -> TreeNode(x2, l2, r2) =>
      x1 == x2 && isSymmetric(l1, r2) && isSymmetric(r1, l2)
    case _ => false
  }

  sealed trait MyList
  object MyList {
    case class ListNode(x: Int = 0, next: MyList = EmptyList) extends MyList
    case object EmptyList                                     extends MyList
  }

  import MyList._

  // 25. Reverse Nodes in k-Group
  def join(left: MyList, right: MyList): MyList = left match {
    case EmptyList      => right
    case ListNode(h, t) => ListNode(h, join(t, right))
  }

  def reverse(list: MyList, res: MyList): MyList = list match {
    case EmptyList       => res
    case ListNode(x, xs) => reverse(xs, ListNode(x, res))
  }

  def reverseKGroup(list: MyList, original: Int): MyList = {
    def go(
        list: MyList,
        tmp: MyList,
        res: MyList,
        k: Int
      ): MyList =
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
  def toScalaList(xs: MyList, res: List[Int] = List.empty[Int]): List[Int] = xs match {
    case EmptyList      => res
    case ListNode(h, t) => toScalaList(t, res :+ h)
  }

  def toMyList(xs: List[Int], res: MyList = EmptyList): MyList = xs match {
    case Nil    => res
    case h :: t => toMyList(t, ListNode(h, res))
  }

  def mergeKLists(lists: Array[ListNode]) =
    toMyList(
      lists
        .flatMap(toScalaList(_))
        .sorted(Ordering.Int.reverse)
        .toList
    )

  val t1 = TreeNode(1, TreeNode(2, TreeNode(3), TreeNode(4)), TreeNode(2, TreeNode(3), TreeNode(4)))
  val t2 = TreeNode(1, TreeNode(2, TreeNode(3), TreeNode(4)), TreeNode(2, TreeNode(4), TreeNode(3)))
  val t3 = TreeNode(1, TreeNode(2, EmptyNode, TreeNode(3)), TreeNode(2, EmptyNode, TreeNode(4)))
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
