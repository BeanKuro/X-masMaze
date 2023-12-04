package mazeGame_TP.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mazeGame_TP.api.Position;

class MazePanel extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private Rectangle2D[][] cells;
    private static final int dimension = 600;
    private int[][] array;
    private int cellsMaze;
    private float cell_size;
    private Position start;
    private Position end;
    private Position current;
    private int delay = 100;
    public Maze maze;
    private ImageIcon santaImage;  // 산타클로스 이미지

    public MazePanel(int cellsMaze) {
        addKeyListener(this);
        refresh(cellsMaze);

        // 산타클로스 이미지 초기화
        santaImage = new ImageIcon("mazeGame_TP\\image\\santa.png");  // 산타클로스 이미지 파일 경로로 수정
    }

    public int getCellsMaze() {
        return this.cellsMaze;
    }

    public void refresh(int cellsMaze) {
        this.cellsMaze = cellsMaze;
        setSize(dimension, dimension);
        cell_size = (float) (1.0 * dimension / cellsMaze);

        setFocusable(true);

        // Maze Object
        maze = new Maze(cellsMaze - 2);
        array = maze.getArray();

        // Set Position
        start = current = new Position(1, 0);
        end = new Position(cellsMaze - 2, cellsMaze - 1);

        // Set maze on panel
        cells = new Rectangle2D[cellsMaze][cellsMaze];
        for (int i = 0; i < cellsMaze; i++) {
            for (int j = 0; j < cellsMaze; j++) {
                cells[i][j] = new Rectangle2D.Double(j * cell_size, i * cell_size, cell_size, cell_size);
            }
        }
    }

    private void drawMaze(Graphics2D g2d) {
        for (int i = 0; i < cellsMaze; i++) {
            for (int j = 0; j < cellsMaze; j++) {
                if (array[i][j] == 0) {
                    g2d.setColor(new Color(129, 102, 0));
                } else {
                    g2d.setColor(new Color(255, 255, 255));
                }
                g2d.fill(cells[i][j]);
            }
        }

        // Fill first cell
        int x = start.getX();
        int y = start.getY();

        g2d.setColor(new Color(129, 102, 0));
        g2d.fill(cells[x][y]);
    }

    public void autoMove(Graphics2D g2d) {
        Stack<Position> way = maze.getDirectWay(start, end);
        g2d.setColor(Color.GREEN);
        while (!way.empty()) {
            Position next = way.pop();
            int x = next.getX();
            int y = next.getY();
            g2d.fill(cells[x][y]);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void algorithm(Graphics2D g2d) {
        Stack<Position> way = maze.getWay(start, end);
        g2d.setColor(Color.GREEN);
        while (!way.empty()) {
            Position next = way.pop();
            int x = next.getX();
            int y = next.getY();
            g2d.fill(cells[x][y]);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean passed() {
        return current.equals(end);
    }

    private void move(Position current) {
    repaint();

    if (passed()) {
        JOptionPane.showMessageDialog(getParent(), "Santa delivered the gift well! You Win!!", "Congratulation", JOptionPane.PLAIN_MESSAGE);
        refresh(this.cellsMaze);
        repaint();
    }
}

    public void hint() {
        Stack<Position> way = maze.getDirectWay(start, end);

        if (!way.isEmpty()) {
            Position current = way.pop();
            int x = current.getX();
            int y = current.getY();

            while (!way.isEmpty()) {
                Position next = way.peek();
                int nextX = next.getX();
                int nextY = next.getY();

                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(Color.RED);
                g2d.fill(cells[nextX][nextY]);

                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        drawMaze((Graphics2D) g);

        int x = current.getX();
        int y = current.getY();
        Image santa = santaImage.getImage();
        g.drawImage(santa, (int) (y * cell_size), (int) (x * cell_size), (int) cell_size, (int) cell_size, this);
    }
	
	public void keyTyped(KeyEvent e) {
	}

	@Override
public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_W) {
        if (maze.canMoveUp(current)) {
            current.setX(current.getX() - 1);
            move(current);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_D) {
        if (maze.canMoveRight(current)) {
            current.setY(current.getY() + 1);
            move(current);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_S) {
        if (maze.canMoveDown(current)) {
            current.setX(current.getX() + 1);
            move(current);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_A) {
        if (maze.canMoveLeft(current)) {
            current.setY(current.getY() - 1);
            move(current);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
        if (maze.canMoveUp(current)) {
            current.setX(current.getX() - 1);
            move(current);
        }       
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        if (maze.canMoveRight(current)) {
            current.setY(current.getY() + 1);
            move(current);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        if (maze.canMoveDown(current)) {
            current.setX(current.getX() + 1);
            move(current);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        if (maze.canMoveLeft(current)) {
            current.setY(current.getY() - 1);
            move(current);
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_H) {
        hint();
    }
}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}