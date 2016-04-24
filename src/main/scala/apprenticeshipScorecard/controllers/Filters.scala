package apprenticeshipScorecard.controllers

object Filters {
  implicit class Filtering[T](xs:Seq[T]) {
    def limit(l:Option[Int]) :Seq[T] = l match {
      case Some(i) => xs.take(i)
      case None => xs
    }
  }
}
