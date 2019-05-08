A Scala Purely Functional Random Access List
============================================

This is a Scala implementation of the _random access list purely functional data structure_, first presented by **Chris Okasaki**. See [here](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.55.5156&rep=rep1&type=pdf). For more information and code explaination, see [my article](https://medium.com/@baseerhk/a-purely-functional-random-access-list-in-scala-d316a7b9c108).

Usage Example:
--------------
```
scala> import randomAccessList.RandomAccessList._
import randomAccessList.RandomAccessList._

scala> val ral = fromList(List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'))
ral: randomAccessList.RandomAccessList.RandomAccessList[Char] = List((Leaf(a),1), (Leaf(b),1), (Node(c,Leaf(d),Leaf(e)),3), (Node(f,Node(g,Leaf(h),Leaf(i)),Node(j,Leaf(k),Leaf(l))),7))
scala> lookup(ral, 10)
res1: Char = k

scala> update(ral, 10, 'K')
res2: randomAccessList.RandomAccessList.RandomAccessList[Char] = List((Leaf(a),1), (Leaf(b),1), (Node(c,Leaf(d),Leaf(e)),3), (Node(f,Node(g,Leaf(h),Leaf(i)),Node(j,Leaf(K),Leaf(l))),7))

scala> ral
res3: randomAccessList.RandomAccessList.RandomAccessList[Char] = List((Leaf(a),1), (Leaf(b),1), (Node(c,Leaf(d),Leaf(e)),3), (Node(f,Node(g,Leaf(h),Leaf(i)),Node(j,Leaf(k),Leaf(l))),7))

scala> head(ral)
res4: Char = a

scala> tail(ral)
res5: randomAccessList.RandomAccessList.RandomAccessList[Char] = List((Leaf(b),1), (Node(c,Leaf(d),Leaf(e)),3), (Node(f,Node(g,Leaf(h),Leaf(i)),Node(j,Leaf(k),Leaf(l))),7))

scala> cons(ral, 'z')
res6: randomAccessList.RandomAccessList.RandomAccessList[Char] = List((Node(z,Leaf(a),Leaf(b)),3), (Node(c,Leaf(d),Leaf(e)),3), (Node(f,Node(g,Leaf(h),Leaf(i)),Node(j,Leaf(k),Leaf(l))),7))

scala> head(cons(ral, 'z'))
res7: Char = z
```

