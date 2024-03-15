import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Evaluable_UF2 {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		int opcion = 0;
		boolean run = true;
		String nombre = "";
		String nombre2 = "";				
		String sql = "";			//CREACIÓN DE VARIABLES QUE VOY A UTILIZAR
		String nombreTabla = "";
		String nombreBBDD = "";
		String nombreFila = "";
		String datoNuevo="";
		int id = 1;
		Connection conect = null; // Creo la variable para realizar la conexion con mi localhost
		Statement importar = null; // Creo las variables para lanzar las consultas SQL
		Statement exportar = null;
		ResultSet rs = null; // creo la variable que me devolverá los datos de la consulta SQL

		System.out.println("||=======================================||");
		System.out.println("||<><><><><><><><><><><><><><><><><><><><||");
		System.out.println("||-----------Gestor de mysql-------------||");
		System.out.println("||<><><><><><><><><><><><><><><><><><><><||");
		System.out.println("||=======================================||");
		System.out.println("");

		System.out.print("Introduce el nombre de la BBDD en la que quieres trabajar:");
		nombreBBDD = input.nextLine();
		try {
			
			//realizo la conexión con DriverManager a mi localhost con el puerto 3306 y doy la opcion de elegir la BBDD en la que trabajar.
			conect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + nombreBBDD, "root", "");

			while (run) {

				// Menú

				System.out.println("Usted esté trabajando en la BBDD de " + nombreBBDD);
				System.out.println("1.-Crear tabla");
				System.out.println("2.-Añadir fila");
				System.out.println("3.-Leer tabla");
				System.out.println("4.-Leer fila");
				System.out.println("5.-Modificar fila");
				System.out.println("6.-Eliminar fila");
				System.out.println("7.-Eliminar tabla");
				System.out.println("0.-Salir");
				System.out.print("Introduzca una opción del menú:");
				opcion = Integer.parseInt(input.nextLine());

				switch (opcion) {

				case 1:// Crear tabla

					System.out.print("Introduce el nombre de la tabla a crear: ");
					nombre = input.nextLine();

					importar = conect.createStatement(); //le damos el valor de la variable creada con la clase connection y creamos un new Statement
					sql = "CREATE TABLE " + nombre
							+ " ( nombre VARCHAR(20) PRIMARY KEY, altura VARCHAR(20), primeraAscension VARCHAR(20), region VARCHAR(20), pais VARCHAR(20))";
					importar.executeUpdate(sql); //lanzamos la consulta SQL con update 

					System.out.println("\n" + "La tabla " + nombre + " se ha creado correctamente." + "\n");
					break;

				case 2: // añadir fila

					System.out.print("Introduce en que tabla quieres trabajar: ");
					nombreTabla = input.nextLine();
					System.out.print("Introduce el nombre: ");
					String nombreMontaña = input.nextLine();
					System.out.print("Introduce la altura: ");
					String altura = input.nextLine();
					System.out.print("Introduce la primera ascension: ");
					String ascension = input.nextLine();
					System.out.print("Introduce la region: ");
					String region = input.nextLine();
					System.out.print("Introduce el pais: ");
					String pais = input.nextLine();

					importar = conect.createStatement();//le damos el valor de la variable creada con la clase connection y creamos un new Statement
					sql = "INSERT INTO " + nombreTabla + " (nombre , altura, primeraAscension, region, pais) VALUES ( '"
							+ nombreMontaña + "' ,'" + altura + "','" + ascension + "', '" + region + "', '" + pais
							+ "')";
					importar.executeUpdate(sql);//lanzamos la consulta SQL con update 
					System.out.println(" La montaña " + nombreMontaña + " ha sido introducida.");
					break;

				case 3: // Leer tabla

					System.out.print("Introduce que tabla quieres leer: ");
					nombreTabla = input.nextLine();

					sql = "SELECT * FROM " + nombreTabla;

					exportar = conect.createStatement();//le damos el valor de la variable creada con la clase connection y creamos un new Statement
					rs = exportar.executeQuery(sql); // le damos a Resulset el valor del Statement que hemos creado anteriormente pero en este caso
														//será un executeQuery para recibir el valor que nos devuelva la consulta.
					while (rs.next()) {
						nombreMontaña = rs.getString("nombre");
						altura = rs.getString("altura");
						ascension = rs.getString("primeraAscension"); 	//bucle while para recibir los valores de los datos de cada tabla
						region = rs.getString("region");
						pais = rs.getString("pais");
						System.out.println(
								"-Nombre: " + nombreMontaña + " | -Altura: " + altura + " | -Primera ascension: "
										+ ascension + " | -region: " + region + " | -Pais: " + pais);
					}break;
				case 4:

					System.out.print("Introduce la tabla en la que quieres leer: ");
					nombreTabla = input.nextLine();
					System.out.print("Introduce el nombre de la montaña que quieres leer: ");
					nombreMontaña = input.nextLine();

					sql = "SELECT * FROM " + nombreTabla + " WHERE nombre = '" + nombreMontaña + "'";
					exportar = conect.createStatement();//le damos el valor de la variable creada con la clase connection y creamos un new Statement
					rs = exportar.executeQuery(sql);// le damos a Resulset el valor del Statement que hemos creado anteriormente pero en este caso
														//será un executeQuery para recibir el valor que nos devuelva la consulta.
					while (rs.next()) {
						nombreMontaña = rs.getString("nombre");
						altura = rs.getString("altura");
						ascension = rs.getString("primeraAscension");			//bucle while para recibir los valores de los datos de cada tabla
						region = rs.getString("region");
						pais = rs.getString("pais");
						System.out.println(
								"-Nombre: " + nombreMontaña + " | -Altura: " + altura + " | -Primera ascension: "
										+ ascension + " | -region: " + region + " | -Pais: " + pais);
					}
					break;

				case 5:// modificar fila

					System.out.println("¿Que campos desea editar?" + "\n" + "Nombre | Altura | Primera Ascension | Region | Pais");
					System.out.println("1.-Nombre");
					System.out.println("2.-Altura");
					System.out.println("3.-Primera ascension");
					System.out.println("4.-Region");
					System.out.println("5.-Pais");
					System.out.println("6.-Todos");
					opcion = Integer.parseInt(input.nextLine());

					if (opcion == 1) {
						System.out.print("¿De que tabla?: ");
						nombreTabla = input.nextLine();
						System.out.print("Introduzca el nombre a editar: ");
						nombreMontaña=input.nextLine();
						System.out.print("Introduzca el nuevo nombre: ");
						datoNuevo=input.nextLine();
						importar = conect.createStatement();
						
						
						sql = "UPDATE " + nombreTabla + " SET nombre = '" + datoNuevo + "' WHERE nombre = '" + nombreMontaña + "'";
						
						importar.executeUpdate(sql);	//lanzamos la consulta SQL con update
						
						System.out.println("El nombre de la montaña se ha actualizado por " + datoNuevo);

					}
					else if (opcion == 2) {
						System.out.print("¿De que tabla?: ");
						nombreTabla = input.nextLine();
						System.out.print("Introduzca la altura a editar: ");
						altura=input.nextLine();
						System.out.print("Introduzca la nueva altura: ");
						datoNuevo=input.nextLine();
						importar = conect.createStatement();
						sql = "UPDATE " + nombreTabla + " SET altura = '" + datoNuevo + "' WHERE altura = '" + altura + "'";
						
						importar.executeUpdate(sql);	//lanzamos la consulta SQL con update
						
						System.out.println("La altura de la montaña se ha actualizado por " + datoNuevo);
					}
					else if (opcion == 3) {
						System.out.print("¿De que tabla?: ");
						nombreTabla = input.nextLine();
						System.out.print("Introduzca la fecha de la primera ascension a editar: ");
						ascension=input.nextLine();
						System.out.print("Introduzca la nueva fecha de la primera ascension: ");
						datoNuevo =input.nextLine();
						importar = conect.createStatement();
						sql = "UPDATE " + nombreTabla + " SET primeraAscension = '" + datoNuevo + "' WHERE primeraAscension = '" + ascension + "'";
						
						importar.executeUpdate(sql);	//lanzamos la consulta SQL con update
						
						System.out.println("La primera ascension de la montaña se ha actualizado por " + datoNuevo);
					}
					else if(opcion == 4) {
						System.out.print("¿De que tabla?: ");
						nombreTabla = input.nextLine();
						System.out.print("Introduzca la region a editar: ");
						region=input.nextLine();
						System.out.print("Introduzca la nueva region: ");
						datoNuevo=input.nextLine();
						importar = conect.createStatement();
						sql = "UPDATE " + nombreTabla + " SET region = '" + datoNuevo + "' WHERE region = '" + region + "'";
						
						importar.executeUpdate(sql);	//lanzamos la consulta SQL con update
						
						System.out.println("La region de la montaña se ha actualizado por " + datoNuevo);
					}
					else if (opcion == 5) {
						System.out.print("¿De que tabla?: ");
						nombreTabla = input.nextLine();
						System.out.print("Introduzca el pais a editar: ");
						pais=input.nextLine();
						System.out.print("Introduzca el nuevo pais : ");
						datoNuevo=input.nextLine();
						importar = conect.createStatement();
						sql = "UPDATE " + nombreTabla + " SET pais = '" + datoNuevo + "' WHERE pais = '" + pais + "'";
						
						importar.executeUpdate(sql);	//lanzamos la consulta SQL con update
						
						System.out.println("El pais ha actualizado por " + datoNuevo);
					}
					else if (opcion == 6) {
						System.out.print("¿De que tabla?: ");
						nombreTabla = input.nextLine();
						System.out.print("Introduce el nombre: ");
						nombreMontaña = input.nextLine();
						System.out.print("Introduce la altura: ");
						altura = input.nextLine();
						System.out.print("Introduce la primera ascension: ");
						ascension = input.nextLine();
						System.out.print("Introduce la region: ");
						region = input.nextLine();
						System.out.print("Introduce el pais: ");
						pais = input.nextLine();
						
						//lanzamos las consultas SQL con updates 
						importar = conect.createStatement();
						sql = "UPDATE " + nombreTabla + " SET nombre = '" + nombreMontaña + "'"; 
						importar.executeUpdate(sql);
						sql = "UPDATE " + nombreTabla + " SET nombre = '" + altura + "'"; 
						importar.executeUpdate(sql);
						sql = "UPDATE " + nombreTabla + " SET nombre = '" + ascension + "'"; 
						importar.executeUpdate(sql);
						sql = "UPDATE " + nombreTabla + " SET nombre = '" + region + "'"; 
						importar.executeUpdate(sql);
						sql = "UPDATE " + nombreTabla + " SET nombre = '" + pais + "'"; 
						importar.executeUpdate(sql);
						System.out.println("La tabla " + nombreTabla + " se ha actualizado correctamente");
					}
					else {
						System.out.println("Opcion erronea.");
						break;
					}
					break;
				case 6:	//Eliminar fila
					
					System.out.print("Indica la tabla en la que deseas eliminar la montaña: ");
					nombreTabla=input.nextLine();
					System.out.print("Indica el nombre de la montaña que deseas eliminar: ");
					nombreMontaña=input.nextLine();
					
					importar = conect.createStatement();
					sql = "DELETE FROM " + nombreTabla + " WHERE nombre = '" + nombreMontaña +"'";
					importar.executeUpdate(sql);	//lanzamos la consulta SQL con update
					System.out.println(" El nombre de la montaña " + nombreMontaña + " se ha eliminado");
					break;
				
				case 7://Eliminar tabla
					
					System.out.print("Introduce el nombre de la tabla a borrar: ");
					nombreTabla=input.nextLine();
					
					importar = conect.createStatement();
					
					sql= "DROP TABLE " + nombreTabla; 
					importar.executeUpdate(sql); 	//lanzamos la consulta SQL con update
					System.out.println("La tabla " + nombreTabla + " se ha borrado.");
					
				case 0:
					run = false;
					conect.close();
					System.out.println("EL programa ha finalizado.");
				
				}	
			}
			
		} catch (SQLException e) {

			System.out.println("Ha habido un error en la conexión");

			e.printStackTrace();
		}

	}

}

