/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud4sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class BancoDados {
    final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final String dataBase_url = "jdbc:sqlserver://localhost:1433;databaseName=Crud";
    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;
    private ArrayList<Cliente> listaCliente = new ArrayList();
    
    // Pega toda a informação do banco de dados e coloca em um ArrayList
    public ArrayList<Cliente> dadosBanco() throws SQLException, ClassNotFoundException{
        
        conexaoBD();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // Criação do ArrayList para armazenar as informações do banco de dados
        ArrayList<Cliente> clientes = new ArrayList();
        
        try {
            
            // Query para retornar todas as informações do banco de dados
            stmt = connection.prepareStatement("SELECT * FROM CLIENTE");
            rs = stmt.executeQuery();
            
            // Enquanto houver registros no banco de dados vai ser executado os seguintes procedimentos
            while (rs.next()){
                // Criação de um cliente
                Cliente cliente = new Cliente();
                
                // Pega as informações do banco e passa para a instância de cliente como atributos
                cliente.setCod(Integer.toString(rs.getInt("COD_CLIENTE")));
                cliente.setNome(rs.getString("NOME_CLIENTE"));
                cliente.setNasc(rs.getString("NASCIMENTO_CLIENTE"));
                cliente.setCpf(rs.getString("CPF_CLIENTE"));
                
                // Adiciona a instância de cliente no ArrayList
                clientes.add(cliente);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } finally{
            fecharConexaoBD();
        }
        
        // Retorna o ArrayList com todas as informações do banco de dados
        return clientes;
    }
    
    public ArrayList<Cliente> pesquisaPorDesc(String desc) throws SQLException, ClassNotFoundException{// Pega a informação selecionada através da pesquisa pelo nome e coloca em um ArrayList
        
        conexaoBD();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        // Criação do ArrayList para armazenar as informações do banco de dados que se encaixam com a pesquisa
        ArrayList<Cliente> clientes = new ArrayList();
        
        try {
            // Query para retornar as informações do banco de dados a partir da busca pelo nome
            stmt = connection.prepareStatement("SELECT * FROM CLIENTE WHERE NOME_CLIENTE LIKE (?)");
            stmt.setString(1, "%"+desc+"%");
            rs = stmt.executeQuery();
            
            // Enquanto houver registros que se encaixam com a pesquisa vai ser executado os seguintes procedimentos
            while (rs.next()){
                // Criação de um cliente
                Cliente cliente = new Cliente();
                
                // Pega as informações do banco e passa para a instância de cliente como atributos
                cliente.setCod(Integer.toString(rs.getInt("COD_CLIENTE")));
                cliente.setNome(rs.getString("NOME_CLIENTE"));
                cliente.setNasc(rs.getString("NASCIMENTO_CLIENTE"));
                cliente.setCpf(rs.getString("CPF_CLIENTE"));
                
                // Adiciona a instância de cliente no ArrayList
                clientes.add(cliente);
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } finally{
            fecharConexaoBD();
        }
        
        // Retorna o ArrayList com as informações do banco de dados a partir da pesquisa pelo nome
        return clientes;
    }
    
    private void fecharConexaoBD(){
        try{
            if(connection != null){
                connection.close();
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    // Construtor que também serve para conectar com o banco de dados
    private void conexaoBD() throws SQLException, ClassNotFoundException{
        try{
            Class.forName(driver);
            
            connection = DriverManager.getConnection(dataBase_url, "sa", "masterkey");
        } catch(SQLException|ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        }    
    }
    
    // Método para inserir dados no banco
    public void inserirDados(String nome, String nasc, String cpf) throws SQLException, ClassNotFoundException{
        conexaoBD();
        PreparedStatement statement = null;
        try{
            
            // Comando para inserir os dados no banco
            statement = connection.prepareStatement("INSERT INTO CLIENTE(NOME_CLIENTE, NASCIMENTO_CLIENTE, CPF_CLIENTE) VALUES(?, ?, ?)");
            statement.setString(1, nome);
            statement.setString(2, nasc);
            statement.setString(3, cpf);
            statement.executeUpdate();
            
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        } finally {
            fecharConexaoBD();
        }
    }
    
    
    // Método para excluir um registro do banco
    public void excluirDados(int cod) throws SQLException, ClassNotFoundException{
        conexaoBD();
        try{
            
            // Comando para excluir o registro do cliente a partir do código passado como parâmetro
            java.sql.PreparedStatement statement = connection.prepareStatement("DELETE FROM CLIENTE WHERE COD_CLIENTE = (?)");
            statement.setInt(1, cod);
            statement.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } finally {
            fecharConexaoBD();
        }   
    }
    
    // Método para atualizar um dado já existente no banco
    public void atualizarDados(int cod, String nome, String nasc, String cpf) throws SQLException, ClassNotFoundException{
        conexaoBD();
        PreparedStatement stmt = null;
        
        try {
            // Comando para fazer a atualização do banco
            stmt = connection.prepareStatement("UPDATE CLIENTE SET NOME_CLIENTE = (?), NASCIMENTO_CLIENTE = (?), CPF_CLIENTE = (?) WHERE COD_CLIENTE = (?);");
            stmt.setString(1, nome);
            stmt.setString(2, nasc);
            stmt.setString(3, cpf);
            stmt.setInt(4, cod);
            stmt.executeUpdate();
            
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } finally{
            fecharConexaoBD();
        }
    }

    /**
     * @return the listaCliente
     */
    public ArrayList<Cliente> getListaCliente() {
        return listaCliente;
    }

    /**
     * @param listaCliente the listaCliente to set
     */
    public void setListaCliente(ArrayList<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }
}
