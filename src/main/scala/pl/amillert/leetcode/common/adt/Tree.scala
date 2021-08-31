package pl.amillert
package leetcode
package common
package adt

sealed trait Tree[+A]
object Tree {
  case object EmptyTree extends Tree[Nothing]
  case class TreeNode[+A](
      value: A,
      left: Tree[A] = EmptyTree,
      right: Tree[A] = EmptyTree
    ) extends Tree[A]
}
