import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class AppLerAquivos extends JFrame {

    private JTextArea textArea;
    private JTextField jTfArquivo1;
    private JTextField jTfArquivo2;
    private JTextField jTfTempo1;
    private JTextField jTfTempo2;
    private JProgressBar progresso1;
    private JProgressBar progresso2;

    public AppLerAquivos() {
        // Configurando o JFrame
        setTitle("Leitor de Arquivo Texto MultiThreading");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Inicializando o JTextArea
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));

        // Adicionando o JTextArea a um JScrollPane para permitir rolagem
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(310, 10, 260, 340);
        add(scrollPane);

        Border gray = BorderFactory.createLineBorder(Color.GRAY);

        scrollPane.setBorder(gray);

        JLabel arquivo1 = new JLabel("Progresso de leitura do arquivo 1:");
        arquivo1.setBounds(10, 10, 280, 25);

        jTfArquivo1 = new JTextField();
        jTfArquivo1.setBounds(10, 35, 280, 30);

        progresso1 = new JProgressBar(0, 100);
        progresso1.setBounds(10, 70, 280, 20);
        progresso1.setStringPainted(true);

        JLabel tempo1 = new JLabel("Tempo da tarefa 1:");
        tempo1.setBounds(10, 103, 180, 25);

        jTfTempo1 = new JTextField();
        jTfTempo1.setBounds(150, 100, 140, 30);
        jTfTempo1.setText("100");

        JLabel arquivo2 = new JLabel("Progresso de leitura do arquivo 2:");
        arquivo2.setBounds(10, 140, 280, 25);

        jTfArquivo2 = new JTextField();
        jTfArquivo2.setBounds(10, 165, 280, 30);

        progresso2 = new JProgressBar(0, 100);
        progresso2.setBounds(10, 200, 280, 20);
        progresso2.setStringPainted(true);

        JLabel tempo2 = new JLabel("Tempo da tarefa 2:");
        tempo2.setBounds(10, 233, 180, 25);

        jTfTempo2 = new JTextField();
        jTfTempo2.setBounds(150, 230, 140, 30);
        jTfTempo2.setText("100");

        JLabel texto = new JLabel("Texto de Entrada:");
        texto.setBounds(10, 345, 200, 25);

        JTextField jTfTexto = new JTextField();
        jTfTexto.setBounds(10, 370, 560, 30);

        add(jTfTempo1);
        add(jTfTempo2);
        add(progresso1);
        add(progresso2);
        add(tempo1);
        add(tempo2);

        add(arquivo1);
        add(arquivo2);
        add(texto);
        // add(scrollPane, null);
        add(jTfArquivo1);
        add(jTfArquivo2);
        add(jTfTexto);

    }

    public void LerArquivosSimultaneo(String filePath1, String filePath2) {
        // Criando instâncias de FileLoaderTask para cada arquivo
        LerArquivos task1 = new LerArquivos(filePath1, textArea, jTfArquivo1, Integer.parseInt(jTfTempo1.getText()),
                progresso1);
        LerArquivos task2 = new LerArquivos(filePath2, textArea, jTfArquivo2, Integer.parseInt(jTfTempo2.getText()),
                progresso2);

        // Criando threads para cada tarefa de leitura de arquivo
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        // Iniciando as threads
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppLerAquivos gui = new AppLerAquivos();
                JButton button = new JButton("Iniciar a leitura");
                button.setBounds(10, 300, 280, 30);
                gui.add(button);

                button.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gui.textArea.setText("");
                        gui.LerArquivosSimultaneo("C:\\Users\\cardo\\OneDrive\\Documentos\\GitHub\\MultiThreads\\MultiTarefas/arquivo1.txt", 
                                                    "C:\\Users\\cardo\\OneDrive\\Documentos\\GitHub\\MultiThreads\\MultiTarefas/arquivo2.txt");

                        

                    }
                });
                gui.setVisible(true);

            }
        });
    }

}
