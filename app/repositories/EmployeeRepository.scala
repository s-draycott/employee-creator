package repositories

import com.google.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.Employee
import slick.lifted.TableQuery
import tables.EmployeeTable
import slick.jdbc.MySQLProfile.api._


import scala.concurrent.{ExecutionContext, Future}
//The repository is a class that handles all communication between your application and the database.

/*
@Inject():
Play injects the configured database for us automatically from application.conf.

DatabaseConfigProvider:
Gives you access to the Slick database configuration (like driver, URL, credentials).
Helps support multiple databases if needed.

dbConfig.db:
This is what you’ll use to actually run Slick queries.

ExecutionContext:
Needed to run database actions asynchronously — so your app stays responsive.
 */

@Singleton //means that only one is created across the app, better for memory
class EmployeeRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val db = dbConfig.db

  //this allows us to run queries
  private val employees = TableQuery[EmployeeTable]

  //function to list all employees - returns a future list of employee objects
  def listEmployees(): Future[Seq[Employee]] = {
    db.run(employees.result)
  }

  //Function to create an employee
  def createEmployee(employee: Employee): Future[Employee] = {
    val insertQuery = (employees returning employees.map(_.id)
      into ((emp, id) => emp.copy(id = Some(id)))) += employee

    db.run(insertQuery)
  }

}
