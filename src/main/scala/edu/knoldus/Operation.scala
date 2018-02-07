package edu.knoldus

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Status, TwitterFactory}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Operation {

  val config = ConfigFactory.load("application.conf")
  val consumerKey = config.getString("Twitter.key.consumerKey")
  val consumerSecret = config.getString("Twitter.key.consumerSecret")
  val accessToken = config.getString("Twitter.key.accessToken")
  val accessTokenSecret = config.getString("Twitter.key.accessTokenSecret")
  val logger = Logger.getLogger(this.getClass)


  val configurationBuilder = new ConfigurationBuilder()
    .setDebugEnabled(false)
    .setOAuthConsumerKey(consumerKey)
    .setOAuthConsumerSecret(consumerSecret)
    .setOAuthAccessToken(accessToken)
    .setOAuthAccessTokenSecret(accessTokenSecret)
  val twitter = new TwitterFactory(configurationBuilder.build()).getInstance()

  def getTwitterInstance(search: String): Future[List[Status]] = Future {
    val count = 100
    val query = new Query(search)
    query.setCount(count)
    val result = twitter.search(query).getTweets.asScala.toList
    logger.info("\n Tweets Found :" + result)
    result
  }

  def getNumberOfTweets(search: String): Future[Int] = Future {
    val count = 100
    val query = new Query(search)
    query.setCount(count)
    val result = twitter.search(query).getTweets.asScala.toList
    val tweetsNumber = result.size
    logger.info("\n No of tweetes : " + tweetsNumber)
    tweetsNumber
  }

  def getAvgTweet(search: String): Future[Long] = Future {
    val count = 100
    val query = new Query(search)
    query.setCount(count)
    val result = twitter.search(query).getTweets.asScala.toList
    val startDate = "2018-01-10"
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val oldDate = LocalDate.parse(startDate, formatter)
    val currentDate = "2018-01-11"
    val newDate = LocalDate.parse(currentDate, formatter)
    val diff = newDate.toEpochDay() - oldDate.toEpochDay()
    query.lang("en")
    query.setSince(startDate)
    query.setUntil(currentDate)
    val avgTweet = result.size / diff
    logger.info("\n Average tweetes : " + avgTweet)
    avgTweet
  }

  def getAvgRetweet(search: String): Future[Int] = Future {
    val count = 100
    val query = new Query(search)
    query.setCount(count)
    val result = twitter.search(query).getTweets.asScala.toList
    val tweetsNumber = result.size
    val retweetcount = result.map(_.getRetweetCount)
    logger.info("\n Average Retweet : " + retweetcount.sum / retweetcount.size)
    retweetcount.sum / retweetcount.size
  }

  def averageLikes(search: String): Future[Int] = Future {
    val count = 100
    val query = new Query(search)
    query.setCount(count)
    val result = twitter.search(query).getTweets.asScala.toList
    val likecount = result.map(_.getFavoriteCount)
    logger.info("\n Average Likes tweetes : " + likecount.sum / likecount.size)
    likecount.sum / likecount.size
  }

}
