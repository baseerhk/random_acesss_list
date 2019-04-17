package randomAccessList

object RandomAccessList {
  type RandomAccessList[A] = List[(CompleteBinaryTree[A], Int)]

  def fromList[A](xs: List[A]): RandomAccessList[A] = {
    def sublists[A](xs: List[A]): List[(List[A], Int)] = {
      def inner(xs: List[A], size: Int, acc: List[(List[A], Int)]): List[(List[A], Int)] = size match {
        case 0 => acc
        case s => {
          val s_ = Math.pow(2, Math.floor(Math.log(s + 1) / Math.log(2))).toInt - 1
          inner(xs.take(s - s_), s - s_, (xs.drop(s - s_), s_)::acc)
        }
      }
      inner(xs, xs.length, List())
    }
    sublists(xs) map {pair => (CompleteBinaryTree(pair._1), pair._2)}
  }

  def lookup[A](ral: RandomAccessList[A], index: Int): A = ral match {
    case Nil                                => throw new Exception("Index is not in list.")
    case (tree, size)::_ if index < size    => tree.lookup(size, index)
    case (_, size)::tail if index >= size   => lookup(tail, index - size)
  }

  def update[A](ral: RandomAccessList[A], index: Int, y: A): RandomAccessList[A] = ral match {
    case Nil                                 => throw new Exception("Index is not in list.")
    case (tree, size)::tail if index < size  => (tree.update(size, index, y), size)::tail
    case (tree, size)::tail if index >= size => (tree, size)::update(tail, index - size, y)
  }

  def cons[A](ral: RandomAccessList[A], e: A): RandomAccessList[A] = ral match {
    case (tree1, size1)::(tree2, size2)::tail if size1 == size2 => (Node(e, tree1, tree2), size1 + size2 + 1)::tail
    case (tree1, size1)::(tree2, size2)::tail if size1 != size2 => (Leaf(e), 1)::(tree1, size1)::(tree2, size2)::tail
    case ts                                                     => (Leaf(e), 1)::ts
  }

  def head[A](ral: RandomAccessList[A]): A = ral match {
    case Nil                      => throw new Exception("Head of empty list.")
    case (Leaf(x), _)::tail       => x
    case (Node(x, _, _), _)::tail => x
  }

  def tail[A](ral: RandomAccessList[A]): RandomAccessList[A] = ral match {
    case Nil                         => throw new Exception("Tail of empty list.")
    case (Leaf(_), _)::tail          => tail
    case (Node(_, l, r), size)::tail => (l, size / 2)::(r, size / 2)::tail
  }
}

sealed trait CompleteBinaryTree[A] {
  def lookup(size: Int, index: Int): A = (this, index) match {
    case (Leaf(x), 0)                        => x
    case (Leaf(_), _)                        => throw new Exception("Index is not in list.")
    case (Node(x, _, _), 0)                  => x
    case (Node(x, l, _), i) if i <= size / 2 => l.lookup(size / 2, i - 1)
    case (Node(x, _, r), i) if i > size / 2  => r.lookup(size / 2, i - 1 - size / 2)
  }
  def update(size: Int, index: Int, y: A): CompleteBinaryTree[A] = (this, index) match {
    case (Leaf(_), 0)                        => Leaf(y)
    case (Leaf(_), _)                        => throw new Exception("Index is not in list.")
    case (Node(_, l, r), 0)                  => Node(y, l, r)
    case (Node(x, l, r), i) if i <= size / 2 => Node(x, l.update(size / 2, i - 1, y), r)
    case (Node(x, l, r), i) if i > size / 2  => Node(x, l, r.update(size / 2, i - 1 - size / 2, y))
  }  
}

object CompleteBinaryTree {
  def apply[A](xs: List[A]): CompleteBinaryTree[A] = {
    def inner(xs: List[A], size: Int): CompleteBinaryTree[A] = size match {
      case 1 => Leaf(xs.head)
      case s if Math.pow(2, Math.floor(Math.log(s + 1)/ Math.log(2))).toInt == s + 1 => {
        val t = xs.tail
        val s_ = size / 2
        Node(xs.head, inner(t.take(s_), s_), inner(t.drop(s_), s_))
      }
      case _ => throw new Exception("Size is not a skew binary.")
    }
    inner(xs, xs.length)
  }
}
case class Leaf[A](value: A) extends CompleteBinaryTree[A]
case class Node[A](value: A, l: CompleteBinaryTree[A], r: CompleteBinaryTree[A]) extends CompleteBinaryTree[A]


