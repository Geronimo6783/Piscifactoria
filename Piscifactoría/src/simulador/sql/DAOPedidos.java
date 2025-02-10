package simulador.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import componentes.Logs;
import simulador.sql.dto.DTOCliente;
import simulador.sql.dto.DTOPedido;
import simulador.sql.dto.DTOPedidoUsuarioPez;
import simulador.sql.dto.DTOPez;

/**
 * Clase DAO (Data Access Object) de la base de datos Piscifactoria.
 */
public class DAOPedidos {

    /**
     * Conexión con la base de datos.
     */
    private Connection conexion;

    /**
     * Consulta que obtiene todos los clientes de la base de datos.
     */
    private PreparedStatement consultaClientes;

    /**
     * Consulta que obtiene todos los peces de la base de datos.
     */
    private PreparedStatement consultaPeces;

    /**
     * Consulta que obtiene todos los pedidos de la base de datos.
     */
    private PreparedStatement consultaPedidos;

    /**
     * Consulta que recupera los pedidos que no han sido realizados al 100% con el
     * nombre
     * del cliente, el nombre del pedido, el porcentaje de completado del pedido y
     * el número de
     * referencia del pedido.
     */
    private PreparedStatement consultaPedidosNoRealizados;

    /**
     * Sentencia para la inserción de un cliente.
     */
    private PreparedStatement insercionCliente;

    /**
     * Sentencia para la inserción de un pedido.
     */
    private PreparedStatement insercionPedido;

    /**
     * Setencia para la inserción de un pez.
     */
    private PreparedStatement insercionPez;

    /**
     * Sentencia para actualizar un pedido.
     */
    private PreparedStatement actualizarPedido;

    /**
     * Constructor parametrizado.
     * 
     * @param conexion Conexión con la base de datos.
     */
    public DAOPedidos(Connection conexion) {
        this.conexion = conexion;
        try {
            consultaClientes = conexion.prepareStatement("SELECT * FROM Cliente;");
            consultaPeces = conexion.prepareStatement("SELECT * FROM Pez;");
            consultaPedidos = conexion.prepareStatement("SELECT * FROM Pedido;");
            consultaPedidosNoRealizados = conexion.prepareStatement("SELECT Pedido.Numero_referencia, " +
                    "Cliente.Nombre, Pez.Nombre, (peces_enviados/peces_solicitados) * 100 FROM Pedido INNER JOIN Cliente"
                    +
                    " ON Pedido.fk_id_cliente = Cliente.id INNER JOIN Pez ON Pedido.fk_id_pez = Pez.id WHERE " +
                    "((peces_envidos/peces_solicitados) * 100) != 100 ORDER BY Pez.Nombre ASC;");
            insercionCliente = conexion.prepareStatement("INSERT INTO Cliente (nombre,nif,telefono) VALUES (?,?,?);");
            insercionPedido = conexion.prepareStatement(
                    "INSERT INTO Pedido (fk_id_cliente,fk_id_pez,peces_solicitados,peces_enviados) VALUES (?,?,?,?);");
            insercionPez = conexion.prepareStatement("INSERT INTO Pez (nombre,nombre_cientifico) VALUES (?,?);");
            actualizarPedido = conexion.prepareStatement(
                    "UPDATE Pedido SET peces_enviados = ? WHERE Numero_referencia = ?;");
        } catch (SQLException e) {
            Logs.escribirError("No se han podido generar las consultar a la base de datos.");
        }
    }

    /**
     * Realiza una consulta en la que se obtienen todos los clientes en la base de
     * datos.
     * 
     * @return Clientes en la base de datos.
     */
    public ArrayList<DTOCliente> obtenerClientes() {
        ResultSet resultadoConsulta = null;
        ArrayList<DTOCliente> clientes = new ArrayList<>();

        try {
            resultadoConsulta = consultaClientes.executeQuery();

            while (resultadoConsulta.next()) {
                clientes.add(new DTOCliente(resultadoConsulta.getInt(1), resultadoConsulta.getString(2),
                        resultadoConsulta.getString(3), resultadoConsulta.getString(4)));
            }
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido realizar la consulta a la base de datos.");
        } finally {
            if (resultadoConsulta != null) {
                try {
                    resultadoConsulta.close();
                } catch (SQLException e) {

                }
            }
        }

        return clientes;
    }

    /**
     * Realiza una consulta en la que se obtienen todos los peces en la base de
     * datos.
     * 
     * @return Peces en la base de datos.
     */
    public ArrayList<DTOPez> obtenerPeces() {
        ResultSet resultadoConsulta = null;
        ArrayList<DTOPez> peces = new ArrayList<>();

        try {
            resultadoConsulta = consultaPeces.executeQuery();

            while (resultadoConsulta.next()) {
                peces.add(new DTOPez(resultadoConsulta.getInt(1), resultadoConsulta.getString(2),
                        resultadoConsulta.getString(3)));
            }
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido realizar la consulta a la base de datos.");
        } finally {
            if (resultadoConsulta != null) {
                try {
                    resultadoConsulta.close();
                } catch (SQLException e) {

                }
            }
        }

        return peces;
    }

    /**
     * Realiza una consulta en la que se obtienen todos los pedidos en la base de
     * datos.
     * 
     * @return Pedidos en la base de datos.
     */
    public ArrayList<DTOPedido> obtenerPedidos() {
        ResultSet resultadoConsulta = null;
        ArrayList<DTOPedido> pedidos = new ArrayList<>();

        try {
            resultadoConsulta = consultaPedidos.executeQuery();

            while (resultadoConsulta.next()) {
                pedidos.add(new DTOPedido(resultadoConsulta.getInt(1), resultadoConsulta.getInt(2),
                        resultadoConsulta.getInt(3), resultadoConsulta.getInt(4), resultadoConsulta.getInt(5)));
            }
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido realizar la consulta a la base de datos.");
        } finally {
            if (resultadoConsulta != null) {
                try {
                    resultadoConsulta.close();
                } catch (SQLException e) {

                }
            }
        }

        return pedidos;
    }

    /**
     * Elimina todos los pedidos almacenados en la tabla pedidos.
     */
    public void borrarPedidos() {
        Statement borradoPedidos = null;

        try {
            borradoPedidos = conexion.createStatement();
            borradoPedidos.execute("TRUNCATE Pedido;");
        } catch (SQLException e) {
            Logs.escribirError("No se han podido eliminar los pedidos de la tabla pedidos.");
        } finally {
            if (borradoPedidos != null) {
                try {
                    borradoPedidos.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    /**
     * Obtiene el número de referencia, el nombre del cliente, el nombre del pez y
     * el porcentaje de completado
     * de los pedido que no han sido finalizados.
     * 
     * @return Número de referencia, nombre del cliente, nombre del pez y porcentaje
     *         de completado de los pedidos que no han
     *         sido finalizados.
     */
    public ArrayList<DTOPedidoUsuarioPez> obtenerPedidosNoFinalizados() {
        ResultSet resultadosConsulta = null;
        ArrayList<DTOPedidoUsuarioPez> datos = new ArrayList<>();

        try {
            resultadosConsulta = consultaPedidosNoRealizados.executeQuery();

            while (resultadosConsulta.next()) {
                datos.add(new DTOPedidoUsuarioPez(resultadosConsulta.getInt(1), resultadosConsulta.getString(2),
                        resultadosConsulta.getString(3), resultadosConsulta.getInt(4), resultadosConsulta.getInt(5),
                        resultadosConsulta.getInt(6)));
            }
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido realizar la consulta a la base de datos.");
        } finally {
            if (resultadosConsulta != null) {
                try {
                    resultadosConsulta.close();
                } catch (SQLException e) {

                }
            }
        }

        return datos;
    }

    /**
     * Actualiza la cantidad de peces enviados en un pedido específico.
     * 
     * @param numeroReferencia Número de referencia del pedido.
     * @param cantidad         Nueva cantidad de peces enviados.
     */
    public void actualizarPecesEnviados(int numeroReferencia, int cantidad) {
        try {
            actualizarPedido.setInt(1, cantidad);
            actualizarPedido.setInt(2, numeroReferencia);
            int filasAfectadas = actualizarPedido.executeUpdate();

            if (filasAfectadas == 0) {
                System.out.println("No se encontró el pedido con número de referencia: " + numeroReferencia);
            }
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido realizar actualizar el pedido.");
        }
    }

    /**
     * Permite la inserción de un nuevo cliente en la base de datos.
     * 
     * @param cliente Cliente nuevo a insertar.
     */
    public void insertarCliente(DTOCliente cliente) {
        try {
            insercionCliente.setString(1, cliente.getNombre());
            insercionCliente.setString(2, cliente.getNif());
            insercionCliente.setString(3, cliente.getTelefono());
            insercionCliente.executeUpdate();
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido realizar la consulta a la base de datos.");
        }
    }

    /**
     * Permite la inserción de un nuevo pedido en la base de datos.
     * 
     * @param pedido Pedido nuevo a insertar.
     */
    public void insertarPedido(DTOPedido pedido) {
        try {
            insercionPedido.setInt(1, pedido.getIdCliente());
            insercionPedido.setInt(2, pedido.getIdPez());
            insercionPedido.setInt(3, pedido.getPecesSolicitados());
            insercionPedido.setInt(4, pedido.getPecesEnviados());
            insercionPedido.executeUpdate();
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido insertar el pedido.");
        }
    }

    /**
     * Permite la inserción de un nuevo pez en la base de datos.
     * 
     * @param pez Pez nuevo a insertar.
     */
    public void insertarPez(DTOPez pez) {
        try {
            insercionPez.setString(1, pez.getNombre());
            insercionPez.setString(2, pez.getNombreCientifico());
            insercionPez.executeUpdate();
        } catch (SQLException e) {
            Logs.escribirError("No se ha podido insertar el pez.");
        }
    }

    /**
     * Cierra la conexión a la base de datos y las consultas preparadas.
     */
    public void close() {
        try {
            consultaClientes.close();
        } catch (SQLException e) {

        }

        try {
            consultaPeces.close();
        } catch (SQLException e) {

        }

        try {
            consultaPedidos.close();
        } catch (SQLException e) {

        }

        try {
            conexion.close();
        } catch (SQLException e) {

        }
    }

    public static void main(String[] args) {
        DAOPedidos dao = new DAOPedidos(Conexion.getConexion());
        ArrayList<DTOCliente> clientes = dao.obtenerClientes();
        System.out.println("========== Clientes ==========");
        for (DTOCliente cliente : clientes) {
            System.out.println("Id: " + cliente.getId() + " Nif: " + cliente.getNif() + " Teléfono: "
                    + cliente.getTelefono() + " Nombre: " + cliente.getNombre());
        }
        System.out.println("========== Peces ==========");
        ArrayList<DTOPez> peces = dao.obtenerPeces();
        for (DTOPez pez : peces) {
            System.out.println(" Id: " + pez.getId() + " Nombre: " + pez.getNombre() + " Nombre científico: "
                    + pez.getNombreCientifico());
        }
        System.out.println("========= Pedidos ==========");
        ArrayList<DTOPedido> pedidos = dao.obtenerPedidos();
        for (DTOPedido pedido : pedidos) {
            System.out.println("Número refenrencia: " + pedido.getNumeroReferencia() + "Id cliente: "
                    + pedido.getIdCliente() + " Id pez: " + pedido.getIdPez() + " Peces enviados: "
                    + pedido.getPecesEnviados() + " Peces solicitados: " + pedido.getPecesSolicitados());
        }
        dao.borrarPedidos();
        dao.close();
    }
}
