package ru.invhacks.updater.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.file.Path;

public class DownloadGUI extends JFrame {

    private JProgressBar progressBar;
    private JLabel infoLabel = new JLabel();
    private JLabel logoLabel = new JLabel();
    private JPanel panel = new JPanel(new GridBagLayout());
    private JPanel infoPanel = new JPanel();
    private JButton exitButton = new JButton();
    private int starterWidth = 300;
    private int starterHeight = 100;

    private int downloaded = 0;
    private int total = 0;

    public DownloadGUI(){
        try {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                this.progressBar = new ProgressBar();
            } catch (Exception e){}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotal() {
        return total;
    }

    public void onDownload(String file, int len){
        this.downloaded = this.downloaded + len;
        this.infoLabel.setText("\u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430 " + file +  " " + (this.downloaded / 1024L + "/" + this.total / 1024L + " \u041a\u0431."));
        this.getProgressBar().setValue(Math.min(100, (int)(100.0 * (this.downloaded / (double)this.total))));

        if(this.downloaded == this.total){
            return;
        }
    }

    public void start(){
        this.getProgressBar().setValue(100);
        this.infoLabel.setText("\u0417\u0430\u043f\u0443\u0441\u043a\u002e\u002e\u002e");
    }

    public void unzip(Path file){
        this.infoLabel.setText("\u0420\u0430\u0441\u043f\u0430\u043a\u043e\u0432\u043a\u0430 " + file.toFile().getName());
        this.getProgressBar().setValue(100);
    }

    public void updateDownload(int total) {
        this.total = total;
        this.downloaded = 0;
    }

    public void init(){
        this.setTitle("Yammi Starter");
        this.setResizable(false);
        this.setSize(this.starterWidth, this.starterHeight);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.initComponents();

        MouseAdapter ma = new MouseAdapter() {
            private Point startMouseLoc = new Point(0, 0);
            private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

            public void mouseDragged(MouseEvent me) {
                Point currLoc = me.getLocationOnScreen();
                int x = currLoc.x - this.startMouseLoc.x;
                int y = currLoc.y - this.startMouseLoc.y;

                if (x <= 0) {
                    x = 0;
                }

                if (DownloadGUI.this.getWidth() + x >= this.screen.width) {
                    x = this.screen.width - DownloadGUI.this.getWidth();
                }

                if (y <= 0) {
                    y = 0;
                }

                if (y + DownloadGUI.this.getHeight() >= this.screen.height) {
                    y = this.screen.height - DownloadGUI.this.getHeight();
                }

                DownloadGUI.this.setLocation(x, y);
            }

            public void mousePressed(MouseEvent e) {
                this.startMouseLoc = e.getPoint();
                int x = this.startMouseLoc.x;
                int y = this.startMouseLoc.y;

                if(x >= 280 && y <= 12){
                    System.exit(0);
                }
            }
        };

        this.addMouseMotionListener(ma);
        this.addMouseListener(ma);
        this.show();
    }

    public void destroy(){
        this.hide();
    }

    private void initComponents(){
        this.logoLabel.setIcon(getLogoIcon());

        this.infoLabel.setFont(loadFont("TTNorms-ExtraLight"));
        this.infoLabel.setText("\u0418\u043d\u0438\u0446\u0438\u043b\u0438\u0437\u0430\u0446\u0438\u044f");
        this.infoLabel.setForeground(new Color(0x8e8e90));

        JPanel fuckPanel = new JPanel();
        fuckPanel.setLayout(new GridBagLayout());

        int y = 55;

        fuckPanel.setBounds(0, y, this.starterWidth, 40);
        fuckPanel.setBackground(new Color(0x1E1E1E));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = -1;
        c.insets = new Insets(5, 0, 25, 0);
        fuckPanel.add(this.infoLabel, c);

        /*JPanel progressPanel = new JPanel();
        progressPanel.setLayout(new GridBagLayout());
        progressPanel.setBounds(0, 70, this.starterWidth, 25);
        progressPanel.setBackground(new Color(0x202124));

        progressPanel.add(this.progressBar);*/

        this.getContentPane().setBackground(new Color(0x1E1E1E));
        this.getContentPane().add(progressBar);
        this.getContentPane().add(fuckPanel);
        this.getContentPane().add(this.logoLabel);
    }

    private Font loadFont(String fontName){
        try {
            InputStream inputStream = DownloadGUI.class.getResourceAsStream("/assets/" + fontName + ".ttf");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            byte[] fontBytes = new byte[inputStream.available()];
            for(int i = 0; i < fontBytes.length; i++) {
                fontBytes[i] = dataInputStream.readByte();
            }
            dataInputStream.close();
            inputStream.close();
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(fontBytes);

            Font font = Font.createFont(Font.TRUETYPE_FONT, arrayInputStream);
            font = font.deriveFont(Font.PLAIN, 15);
            ge.registerFont(font);

            arrayInputStream.close();
            return font;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Icon getLogoIcon(){
        try {
            InputStream inputStream = DownloadGUI.class.getResourceAsStream("/assets/background.png");
            Icon icon = new ImageIcon(ImageIO.read(inputStream));
            inputStream.close();
            return icon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JProgressBar getProgressBar()
    {
        return this.progressBar;
    }

    private static class ProgressBar extends JProgressBar
    {
        public ProgressBar() {
            this.setMaximum(100);
            this.setMinimum(0);

            this.setValue(100);
            this.setBackground(new Color(0x1E1E1E));
            this.setLayout(null);
            this.setBounds(45, 85, 207, 5);
            this.setForeground(new Color(0x24B0FF));

            this.setFont(this.getFont().deriveFont(13.0f));
            this.setUI(new BasicProgressBarUI() {
                @Override
                protected Color getSelectionBackground() {
                    return new Color(0x1E1E1E);
                }

                @Override
                protected Color getSelectionForeground() {
                    return new Color(0x1E1E1E);
                }
            });
        }
    }

}
