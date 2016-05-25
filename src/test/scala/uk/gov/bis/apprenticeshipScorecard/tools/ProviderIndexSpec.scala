package uk.gov.bis.apprenticeshipScorecard.tools

import org.scalatest.{FlatSpec, Matchers}
import uk.gov.bis.apprenticeshipScorecard.models.UKPRN

class ProviderIndexSpec extends FlatSpec with Matchers {

  "mergeMaps" should "correctly merge a single map" in {
    ProviderIndex.mergeMaps(Seq(Map("foo" -> UKPRN(1)))) shouldBe Map("foo" -> Set(UKPRN(1)))
  }

  it should "correctly merge two maps" in {
    val map1 = Map("foo" -> UKPRN(1))
    val map2 = Map("foo" -> UKPRN(2))
    ProviderIndex.mergeMaps(Seq(map1, map2)) shouldBe Map("foo" -> Set(UKPRN(1), UKPRN(2)))
  }

  it should "correctly merge three maps" in {
    val map1 = Map("foo" -> UKPRN(1))
    val map2 = Map("foo" -> UKPRN(2))
    val map3 = Map("bar" -> UKPRN(1))
    ProviderIndex.mergeMaps(Seq(map1, map2, map3)) shouldBe Map(
      "foo" -> Set(UKPRN(1), UKPRN(2)),
      "bar" -> Set(UKPRN(1))
    )
  }

  "ProviderIndex" should "match a subject code" in {
    ProviderIndex.lookup("15.1").size shouldBe 173
  }

  it should "match a provider name" in {
    ProviderIndex.lookup("WOODSPEEN").size shouldBe 1
  }

  it should "match the start of a provider name" in {
    val result = ProviderIndex.lookup("WOOD")
    result.size shouldBe 1
    result.head.rank - 1.4444 should be < 0.1
  }

  it should "match two provider names" in {
    val results = ProviderIndex.matchPhrase("WOOD ACCESS MUSIC")
    results.size shouldBe 5

    results.find(_.item.name == "ACCESS TO MUSIC LIMITED").map(_.rank).get  shouldBe 4.0
  }

}
