package edu.knoldus

import org.apache.log4j.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Application extends App {

  val operation = new Operation
  val logger = Logger.getLogger(this.getClass)
  val hashTag: String = "#scala"

  logger.info("Get Tweets based on hashTag \n")
  val listOfFiles = operation.getTwitterInstance(hashTag)
  listOfFiles onComplete {
    case Success(list) => logger.info(listOfFiles)
    case Failure(list) => logger.info("error message")
  }

  logger.info("Get Number Of Tweets based on hashTag \n")
  val noOfTweets = operation.getNumberOfTweets(hashTag)
  noOfTweets onComplete {
    case Success(number) => logger.info(number)
    case Failure(number) => logger.info("error message")
  }


  logger.info("Get Average Tweets based on hashTag \n")
  val averageTweet = operation.getAvgTweet(hashTag)
  averageTweet onComplete {
    case Success(average) => logger.info(average)
    case Failure(average) => logger.info("error message")
  }


  logger.info("Get Average Tweets Per day based on hashTag \n")
  val averageRetweet = operation.getAvgRetweet(hashTag)
  averageTweet onComplete {
    case Success(avgTweet) => logger.info(avgTweet)
    case Failure(avgTweet) => logger.info("error message")
  }


  logger.info("Get Average Likes based on hashTag \n")
  val averageLikes = operation.averageLikes(hashTag)
  averageLikes onComplete {
    case Success(avgLikes) => logger.info(avgLikes)
    case Failure(avgLikes) => logger.info("error message")
  }
  val time = 50000
  Thread.sleep(time)

}
