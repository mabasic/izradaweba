import eu.izradaweba.references

references
  .sortBy(_.name)
  .reverse
  .sortBy(_.yearMade)
  .reverse
  .map(reference => List(reference.yearMade, reference.name))
