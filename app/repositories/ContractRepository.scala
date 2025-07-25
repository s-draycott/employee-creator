package repositories

import com.google.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.Contract
import slick.lifted.TableQuery
import tables.EmployeeTable
import slick.jdbc.MySQLProfile.api._
import tables.ContractTable

import scala.concurrent.Future


//INJECT - allows play to "inject" dependencies and essentially allow for the database connection
//Singleton ensures the repository isn't replicated saving memory
/// import slick.jdbc.MySQLProfile.api._ allows filtering/mapping etx of table queries - this is needed whenever using slick in play
// (implicit ec: ExecutionContext) — needed to run database futures asynchronously. ALL DATABASE ACTIONS ARE ASYNCHRONOUS (NON-BLOCKING)

import scala.concurrent.ExecutionContext
@Singleton
class ContractRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbconfig = dbConfigProvider.get[JdbcProfile]
  //This gives you the Slick database configuration of the type of database configuration (profile) you're using - in my case this is MySQLProfile - it essentially acts a translator for the specific database type to ensure queries are run correctly.
  private val db = dbconfig.db //allows me to use the val db to run queries on my database
  private val contracts = TableQuery[ContractTable]

  //List all contracts
  def listContracts(): Future[Seq[Contract]] = {
    db.run(contracts.result)
  }

  //Create contract
  /* this is function that takes input of type Contract and returns a future contract. The val insert query
  the val insert query says after inserting into the contracts table return the newly generated id
  then we take the inserted contract and the returned ID and combine them into a new contract instance with the id filled in
  and then += contract means to add it on
  db.run actually runs the insert query
  */
  def createContract(contract: Contract): Future[Contract] = {
    val insertQuery = (contracts returning contracts.map(_.id)
      into ((contract, id) => contract.copy(id = Some(id)))
      ) += contract
    db.run(insertQuery)
  }

}
