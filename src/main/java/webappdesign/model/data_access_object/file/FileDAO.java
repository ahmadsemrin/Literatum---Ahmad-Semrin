package webappdesign.model.data_access_object.file;

import webappdesign.model.UploadedFile;
import webappdesign.model.User;
import webappdesign.util.ErrorHandlerUtil;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class FileDAO implements IFileDAO {
    private List<UploadedFile> fileList;

    private Connection connection;
    private Statement statement;

    private static IFileDAO _instance;
    private static final Object MUTEX = new Object();

    private FileDAO() {
        prepareFiles();

        establishConnectionWithDB();

        createDB();

        createTable();

        getAllFilesFromDB();
    }

    private void prepareFiles() {
        fileList = new ArrayList<>();
    }

    private void establishConnectionWithDB() {
        try {
            prepareMySQLDBDriver();

            createConnection();

            createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void prepareMySQLDBDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306",
                "root","123");
    }

    private void createStatement() throws SQLException {
        statement = connection.createStatement();
    }

    private void createDB() {
        String sqlQuery = "CREATE DATABASE IF NOT EXISTS Literatum";
        try {
            executeSQLQuery(sqlQuery);

            System.out.println("Database \"Literatum\" has created successfully.");
        } catch (SQLException ex) {
            System.out.println("Database \"Literatum\" is already created.");
        }
    }

    private void executeSQLQuery(String sqlQuery) throws SQLException {
        synchronized (MUTEX) {
            statement.executeUpdate(sqlQuery);
        }
    }

    private void createTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS Literatum.File (" +
                "name VARCHAR(25) NOT NULL," +
                "email VARCHAR(50) NOT NULL," +
                "file VARCHAR(100) NOT NULL," +
                "date VARCHAR(50) NOT NULL," +
                "status VARCHAR(10) NOT NULL," +
                "extractedFile VARCHAR(25))";
        try {
            executeSQLQuery(sqlQuery);

            System.out.println("Table \"File\" has created successfully.");
        } catch (SQLException ex) {
            System.out.println("Table \"File\" is already created.");
        }
    }

    private void getAllFilesFromDB() {
        String sqlQuery = "SELECT * FROM Literatum.File";

        ResultSet resultSet = null;
        try {
            resultSet = getResultSetFormSQLQuery(sqlQuery);

            addFilesFromResultSet(resultSet);
        } catch (SQLException ex) {
            System.out.println("Can't get the files.");
        } finally {
            closeResultSet(resultSet);
        }
    }

    private ResultSet getResultSetFormSQLQuery(String sqlQuery) throws SQLException {
        synchronized (MUTEX) {
            return statement.executeQuery(sqlQuery);
        }
    }

    private void addFilesFromResultSet(ResultSet resultSet) throws SQLException {
        while (isThereMoreColumns(resultSet)) {
            UploadedFile file = getFileFromResultSet(resultSet);

            addFileToFileList(file);
        }
    }

    private boolean isThereMoreColumns(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }

    private UploadedFile getFileFromResultSet(ResultSet resultSet) throws SQLException {
        String name = getNameFromColumn(resultSet);
        String email = getEmailFromColumn(resultSet);
        String fileName = getFileNameFromResultSet(resultSet);
        String date = getDateFromColumn(resultSet);
        String status = getStatusFromColumn(resultSet);
        String extractedFile = getExtractedFileFromColumn(resultSet);

        UploadedFile file = new UploadedFile();
        file.setName(name);
        file.setEmail(email);
        file.setFile(fileName);
        file.setDate(date);
        file.setStatus(status);
        file.setExtractedFile(extractedFile);

        return file;
    }

    private String getNameFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("name");
    }

    private String getFileNameFromResultSet(ResultSet resultSet) throws SQLException {
        return resultSet.getString("file");
    }

    private String getEmailFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("email");
    }

    private String getDateFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("date");
    }

    private String getStatusFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("status");
    }

    private String getExtractedFileFromColumn(ResultSet resultSet) throws SQLException {
        return resultSet.getString("extractedFile");
    }

    private void addFileToFileList(UploadedFile file) {
        fileList.add(file);
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            if (isResultSetNotNull(resultSet)) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isResultSetNotNull(ResultSet resultSet) {
        return resultSet != null;
    }

    public static IFileDAO getInstance() {
        createInstance();

        return _instance;
    }

    private static void createInstance() {
        if (isInstanceNull()) {
            synchronized (MUTEX) {
                if (isInstanceNull()) {
                    _instance = new FileDAO();
                }
            }
        }
    }

    private static boolean isInstanceNull() {
        return _instance == null;
    }

    @Override
    public List<UploadedFile> findAll() {
        return fileList;
    }

    @Override
    public void insertFile(UploadedFile file) {
        String sqlQuery = "INSERT INTO Literatum.File VALUES (?, ?, ?, ?, ?, ?)";

        prepareStatementForSQLQuery(sqlQuery, file);

        addFileToFileList(file);
    }

    private void prepareStatementForSQLQuery(String sqlQuery, UploadedFile file) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, file.getName());
            preparedStatement.setString(2, file.getEmail());
            preparedStatement.setString(3, file.getFile());
            preparedStatement.setString(4, file.getDate());
            preparedStatement.setString(5, file.getStatus());
            preparedStatement.setString(6, file.getExtractedFile());

            synchronized (MUTEX) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    private void displayFailMessageToUser() {
        ErrorHandlerUtil.setFoundFileError();
    }

    private void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            if (isPreparedStatementNotNull(preparedStatement)) {
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isPreparedStatementNotNull(PreparedStatement preparedStatement) {
        return preparedStatement != null;
    }

    @Override
    public void deleteFile(UploadedFile file) {
        String sqlQuery = "DELETE FROM Literatum.File WHERE name = ? AND email = ? AND file = ? AND date = ? AND status = ? AND extractedFile = ?";

        prepareStatementForSQLQuery(sqlQuery, file);

        deleteFileFromFileList(file);
    }

    private void deleteFileFromFileList(UploadedFile file) {
        fileList.remove(file);
    }
}
