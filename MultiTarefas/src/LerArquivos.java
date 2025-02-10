
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LerArquivos implements Runnable {

    private final String filePath;
    private final JTextArea textArea;
    private final JTextField texto;
    private final int tempo;
    private final int totalLinhas;
    private final JProgressBar progressBar;
    private int lineRead;
    int progresso;

    public LerArquivos(String filePath, JTextArea textArea, JTextField text, int tempo, JProgressBar progress) {
        this.filePath = filePath;
        this.textArea = textArea;
        this.tempo = tempo;
        this.texto = text;
        this.totalLinhas = contaTotalLinhas(filePath);
        this.progressBar = progress;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineRead++;
                final int progressoAtual = (int) (((double) lineRead / totalLinhas) * 100);
                final String currentLine = line;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (textArea) {
                            textArea.append(currentLine + "\n");
                        }
                        // Atualiza a barra de progesso
                        progressBar.setValue(progressoAtual); // Atualiza a barra de progresso

                        // Atualiza o JTextField com a linha que está sendo lida
                        texto.setText(currentLine);
                    }
                });
                Thread.sleep(tempo); 
                
            }
        } catch (IOException e) {
            showError("Erro ao ler o arquivo: " + e.getMessage());
        } catch (InterruptedException e) {
            showError("A leitura do arquivo foi interrompida: " + e.getMessage());
        }
    }

    private int contaTotalLinhas(String filePath) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (Exception e) {
            mostrarError("Erro ao contar as linhas do arquivo " + e.getMessage());
        }
        return lineCount;
    }

    private void mostrarError(final String mg) {

    }

    private void showError(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(textArea.getParent(), message, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
