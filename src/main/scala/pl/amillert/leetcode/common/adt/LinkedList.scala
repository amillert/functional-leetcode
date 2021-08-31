package pl.amillert
package leetcode
package common
package adt

sealed trait LinkedList[+A]
object LinkedList {
  case object EmptyList extends LinkedList[Nothing]
  case class ListNode[+A](
      value: A,
      next: LinkedList[A] = EmptyList
    ) extends LinkedList[A]
}
