package pl.amillert
package leetcode
package day3

import common.adt.Tree
import common.adt.Tree._

object Main extends App {

  // 114. Flatten Binary Tree to Linked List
  def merge[A](l: Tree[A], r: Tree[A]): Tree[A] = l match {
    // match may not be exhaustive warning but I know how I constructed tree-list in [[flatten]]
    case EmptyTree => r
    case TreeNode(x, EmptyTree, rt) =>
      TreeNode(x, EmptyTree, merge(rt, r))
  }

  def flatten[A](tree: Tree[A], res: Tree[A] = EmptyTree): Tree[A] = tree match {
    case EmptyTree => res
    case TreeNode(x, l, r) =>
      val left  = flatten(l)
      val right = flatten(r)

      TreeNode(x, EmptyTree, merge(left, right))
  }

  // 98. Validate Binary Search Tree
  def isValidBST(t: Tree[Int]): Boolean = t match {
    case TreeNode(x, l @ TreeNode(lx, _, _), r @ TreeNode(rx, _, _)) =>
      lx < x && rx > x && isValidBST(l) && isValidBST(r)
    case _ => true
  }

  // 104. Maximum Depth of Binary Tree
  def maxDepth(
      t: Tree[Int],
      level: Int = 1
    ): Int = t match {
    case EmptyTree                         => 0
    case TreeNode(_, EmptyTree, EmptyTree) => level
    case TreeNode(_, l, r) =>
      val leftMax  = maxDepth(l, level + 1)
      val rightMax = maxDepth(r, level + 1)

      (leftMax :: rightMax :: Nil).max
  }

  val tree = TreeNode(1, TreeNode(2, TreeNode(3), TreeNode(4)), TreeNode(5, EmptyTree, TreeNode(6)))
  val expected = // format: off
    TreeNode(1, EmptyTree, TreeNode(2, EmptyTree, TreeNode(3, EmptyTree, TreeNode(4, EmptyTree, TreeNode(5, EmptyTree, TreeNode(6, EmptyTree, EmptyTree)))))) // format: on
  assert(flatten(tree) == expected)
  assert(flatten(TreeNode(0)) == TreeNode(0))
  val leftBiasedTree  = TreeNode(1, TreeNode(2, TreeNode(3)))
  val rightBiasedTree = TreeNode(1, EmptyTree, TreeNode(2, EmptyTree, TreeNode(3)))
  assert(flatten(leftBiasedTree) == rightBiasedTree)
  assert(flatten(rightBiasedTree) == rightBiasedTree)

  assert(isValidBST(TreeNode(2, TreeNode(1), TreeNode(3))) == true)
  assert(isValidBST(TreeNode(5, TreeNode(1), TreeNode(4, TreeNode(3), TreeNode(6)))) == false)
  assert(isValidBST(EmptyTree) == true)
  assert(isValidBST(TreeNode(1)) == true)

  assert(maxDepth(EmptyTree) == 0)
  assert(maxDepth(TreeNode(1)) == 1)
  assert(maxDepth(leftBiasedTree) == 3)
  assert(maxDepth(tree) == 3)
  assert(maxDepth(expected) == 6)
}
