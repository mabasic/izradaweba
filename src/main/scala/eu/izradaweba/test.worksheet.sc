// import eu.izradaweba.references
import eu.izradaweba.pages.{Item, products, services}

// references
//   .sortBy(_.name)
//   .reverse
//   .sortBy(_.yearMade)
//   .reverse
//   .map(reference => List(reference.yearMade, reference.nam  /*>  : List[List[Matchable]] = List(List(2022, Bonaventura), Lis…  */e))

case class Subject(
  id: String,
  text: String
)

def itemToSubject(item: Item) =
  Subject(text = item.name, id = item.tag.tag)

val productSubjects = products.map(itemToSubject)  /*>  : List[Subject] = List(Subject(web-shop,Internet trgovina), Subject(boo…  */
val serviceSubjects = services.map(itemToSubject)  /*>  : List[Subject] = List(Subject(web-standard,Web stranice), Subject(cust…  */

val subjects = productSubjects ++ serviceSubjects  /*>  : List[Subject] = List(Subject(web-shop,Internet trgovina), Subject(boo…  */
