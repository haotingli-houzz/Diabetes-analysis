/**
 * @author Hang Su <hangsu@gatech.edu>.
 */

package edu.gatech.cse8803.clustering

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

object Metrics {
  /**
   * Given input RDD with tuples of assigned cluster id by clustering,
   * and corresponding real class. Calculate the purity of clustering.
   * Purity is defined as
   *             \fract{1}{N}\sum_K max_j |w_k \cap c_j|
   * where N is the number of samples, K is number of clusters and j
   * is index of class. w_k denotes the set of samples in k-th cluster
   * and c_j denotes set of samples of class j.
   * @param clusterAssignmentAndLabel RDD in the tuple format
   *                                  (assigned_cluster_id, class)
   * @return purity
   */
  def purity(clusterAssignmentAndLabel: RDD[(Int, Int)]): Double = {
    /**
     * TODO: Remove the placeholder and implement your code here
     */

    def purityCheck (cluster:(Int, Iterable[(Int, Int)])): Double = {

      val maxClass = cluster._2.groupBy(x => x._2).map(x => x._2.map(z => 1.0).reduce(_+_) ).reduce((x, y) => x max y)
      maxClass
    }


    val allSum = clusterAssignmentAndLabel.count().toDouble

    val summed = clusterAssignmentAndLabel.map(x => (x._1, (x._1, x._2))).groupByKey.map(purityCheck).reduce(_+_)

    summed / allSum

  }
}
