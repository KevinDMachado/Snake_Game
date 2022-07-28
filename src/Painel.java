import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class Painel extends JPanel implements ActionListener {

    static final int telaLargura = 600;
    static final int telaAltura = 600;
    static final int unidade = 20;
    static final int escalaDoJogo = (telaLargura * telaAltura) / (unidade * unidade);
    static final int atraso = 125;
    final int x[] = new int[escalaDoJogo];
    final int y[] = new int[escalaDoJogo];
    int tamanhoInicial = 3;
    int comeu = 0;
    int comidaX;
    int comidaY;
    char direcao = 'R';
    boolean rodando = false;
    Timer timer;
    Random random;

    Painel() {
        random = new Random();
        this.setPreferredSize(new Dimension(telaLargura, telaAltura));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new AdaptarTecla());
        iniciarGame();
    }

    public void iniciarGame() {
        novaComida();
        rodando = true;
        timer = new Timer(atraso, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        desenhar(g);
    }

    public void desenhar(Graphics g) {
        if (rodando) {
            g.setColor(Color.red);
            g.fillRect(comidaX, comidaY, unidade, unidade);

            for (int i = 0; i < tamanhoInicial; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unidade, unidade);
                } else {
                    g.setColor(new Color(45, 180, 10));
                    g.fillRect(x[i], y[i], unidade, unidade);

                }
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                FontMetrics metrics1 = getFontMetrics(g.getFont());
                g.drawString("Pontos: " + comeu, (telaLargura - metrics1.stringWidth("Pontos: " + comeu)) / 2,
                        g.getFont().getSize());
            }
        } else {
            fimDeJogo(g);
        }
    }

    public void novaComida() {
        comidaX = random.nextInt((int) (telaLargura / unidade)) * unidade;
        comidaY = random.nextInt((int) (telaAltura / unidade)) * unidade;

    }

    public void movimentar() {
        for (int i = tamanhoInicial; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direcao) {
            case 'U':
                y[0] = y[0] - unidade;
                break;
            case 'D':
                y[0] = y[0] + unidade;
                break;
            case 'L':
                x[0] = x[0] - unidade;
                break;
            case 'R':
                x[0] = x[0] + unidade;
                break;
        }

    }

    public void comida() {
        if ((x[0] == comidaX) && (y[0] == comidaY)) {
            tamanhoInicial++;
            comeu++;
            novaComida();
        }
    }

    public void colisao() {
        for (int i = tamanhoInicial; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                rodando = false;
            }
        }
        if (x[0] < 0) {
            rodando = false;
        }
        if (x[0] > telaLargura - unidade) {
            rodando = false;
        }
        if (y[0] < 0) {
            rodando = false;
        }
        if (y[0] > telaAltura - unidade) {
            rodando = false;
        }
        if (!rodando) {
            timer.stop();
        }
    }

    public void fimDeJogo(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Pontos: " + comeu, (telaLargura - metrics1.stringWidth("Pontos: " + comeu)) / 2,
                g.getFont().getSize());

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("FIM DE JOGO!", (telaLargura - metrics2.stringWidth("FIM DE JOGO!")) / 2, telaAltura / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (rodando) {
            movimentar();
            comida();
            colisao();
        }
        repaint();
    }

    public class AdaptarTecla extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direcao != 'R') {
                        direcao = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direcao != 'L') {
                        direcao = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direcao != 'D') {
                        direcao = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direcao != 'U') {
                        direcao = 'D';
                    }
                    break;
            }
        }
    }
}
