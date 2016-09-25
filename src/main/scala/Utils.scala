import org.apache.commons.cli.{Options, ParseException, PosixParser}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.feature.HashingTF
import twitter4j.auth.OAuthAuthorization
import twitter4j.conf.ConfigurationBuilder

object Utils {

  val numFeatures = 1000
  val tf = new HashingTF(numFeatures)

  val CONSUMER_KEY = "Duf4frCzqeGwFKUnnKt0NDHeg"
  val CONSUMER_SECRET = "IUhrpVXpqXHYVbIB3uD4TP3IPqhA0qVk6F4lzFQFivn4wmuoh0"
  val ACCESS_TOKEN = "502725838-injrtReyq8vInJRHImZV6ToYvXx4QPHpxwwpxmsd"
  val ACCESS_TOKEN_SECRET = "SAqdx4MMjWNnf1vpYjPJ9wdRaXvov8eT1Qe4xtCPsKyps"

  val THE_OPTIONS = {
    val options = new Options()
    options
  }

  def parseCommandLineWithTwitterCredentials(args: Array[String]) = {
    val parser = new PosixParser
    try {
      val cl = parser.parse(THE_OPTIONS, args)
      System.setProperty("twitter4j.oauth.consumerKey", CONSUMER_KEY)
      System.setProperty("twitter4j.oauth.consumerSecret", CONSUMER_SECRET)
      System.setProperty("twitter4j.oauth.accessToken", ACCESS_TOKEN)
      System.setProperty("twitter4j.oauth.accessTokenSecret", ACCESS_TOKEN_SECRET)
      cl.getArgList.toArray
    } catch {
      case e: ParseException =>
        System.err.println("Parsing failed.  Reason: " + e.getMessage)
        System.exit(1)
    }
  }

  def getAuth = {
    Some(new OAuthAuthorization(new ConfigurationBuilder().build()))
  }

  /**
   * Create feature vectors by turning each tweet into bigrams of characters (an n-gram model)
   * and then hashing those to a length-1000 feature vector that we can pass to MLlib.
   * This is a common way to decrease the number of features in a model while still
   * getting excellent accuracy (otherwise every pair of Unicode characters would
   * potentially be a feature).
   */
  def featurize(s: String): Vector = {
    tf.transform(s.sliding(2).toSeq)
  }

  object IntParam {
    def unapply(str: String): Option[Int] = {
      try {
        Some(str.toInt)
      } catch {
        case e: NumberFormatException => None
      }
    }
  }
}
