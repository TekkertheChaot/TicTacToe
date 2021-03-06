package window;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class TicTacToe extends JFrame {
	private JTextPane consolePanel = new JTextPane();
	private JScrollPane scrollPaneCMD = new JScrollPane();
	private JCheckBox chkbxConsoleEnabled = new JCheckBox("Zeige Konsole");
	private JButton btnRestart = new JButton("Neu Starten");
	private JButton btn22 = new JButton("");
	private JButton btn12 = new JButton("");
	private JButton btn02 = new JButton("");
	private JButton btn21 = new JButton("");
	private JButton btn11 = new JButton("");
	private JButton btn01 = new JButton("");
	private JButton btn20 = new JButton("");
	private JButton btn10 = new JButton("");
	private JButton btn00 = new JButton("");
	private JPanel contentPane;
	JButton[][] playfield = new JButton[][] { { btn00, btn10, btn20 }, { btn01, btn11, btn21 },
			{ btn02, btn12, btn22 } };

	enum Player {
		Human, Com
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToe frame = new TicTacToe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	protected void highlightWin(JButton[] buttons) {
		for(JButton jb : buttons) {
			jb.setBackground(Color.GREEN);
		}
	}

	protected void end(Player player) {
		List<JButton[]> jbl = Arrays.asList(playfield);
		for (JButton[] jba : jbl) {
			List<JButton> jbil = Arrays.asList(jba);
			for (JButton jbtn : jbil) {
				jbtn.setEnabled(false);
			}
		}
		switch (player) {
		case Human:
			consolePanel.setText(consolePanel.getText() + "\n\nDu hast gewonnen!");
			break;
		case Com:
			consolePanel.setText(consolePanel.getText() + "\n\nComputer hat gewonnen!");
			break;
		default:
			break;
		}
	}

	protected boolean checkWin(JButton jbclicked) {
		if (checkHorizontal(jbclicked) || checkVertical(jbclicked) || checkDiagonal(jbclicked))
			return true;
		else
			return false;
	}
	
	protected boolean checkDiagonal(JButton jbclicked) {
		if(getJButtonByCoordinates(new int[] {1,1}).getText()!="") {
			int[][] cross = new int[][] {
				{0,0},	{2,0},
					{1,1},
				{0,2},	{2,2}
			};
			for(int[] cell : cross) {
				if(Arrays.equals(getJButtonArrangement(jbclicked), cell)) {
					String[] txtVal = new String[5];
					for(int i = 0; i<5; i++) {
						txtVal[i] = getJButtonByCoordinates(cross[i]).getText();
					}
					if(txtVal[0]==txtVal[2] && txtVal[2]==txtVal[4]) {
						JButton[] buttons = new JButton[] {
								getJButtonByCoordinates(0,0),
								getJButtonByCoordinates(1,1),
								getJButtonByCoordinates(2,2)
						};
						highlightWin(buttons);
						return true;
					} else if(txtVal[1]==txtVal[2] && txtVal[2]==txtVal[3]) {
						JButton[] buttons = new JButton[] {
								getJButtonByCoordinates(2,0),
								getJButtonByCoordinates(1,1),
								getJButtonByCoordinates(0,2)
						};
						highlightWin(buttons);
						return true;
					}
				}
			}
		}
		return false;
	}

	protected boolean checkHorizontal(JButton jbclicked) {
		JButton[] row = Arrays.asList(playfield).get(getJButtonArrangement(jbclicked)[1]);
		if (row[0].getText().equals(row[1].getText()) && row[2].getText().equals(row[1].getText())) {
			highlightWin(row);
			return true;
		} else
			return false;
	}

	protected boolean checkVertical(JButton jbclicked) {
		int column = getJButtonArrangement(jbclicked)[0];
		List<JButton[]> rows = Arrays.asList(playfield);
		if (rows.get(0)[column].getText().equals(rows.get(1)[column].getText()) && rows.get(2)[column].getText().equals(rows.get(1)[column].getText())) {
			JButton[] buttons = new JButton[] {
					getJButtonByCoordinates(column,0),
					getJButtonByCoordinates(column,1),
					getJButtonByCoordinates(column,2)
			};
			highlightWin(buttons);
			return true;
		} else
			return false;
	}

	protected void afterTurn(JButton jbclicked, Player player) {
		if (checkWin(jbclicked))
			end(player);
		else if (getEnabledJButtons().size() != 0) {
			switch (player) {
			case Human:
				comTurn();
				break;
			case Com:
				break;
			default:
				System.out.println("You're stupid!");
				break;
			} 
		}else consolePanel.setText(consolePanel.getText()+"\n\nEin Unentschieden!");
	}

	protected List<JButton> getEnabledJButtons() {
		List<JButton> available = new ArrayList<>();
		List<JButton[]> jbl = Arrays.asList(playfield);
		for (JButton[] jba : jbl) {
			List<JButton> jbil = Arrays.asList(jba);
			for (JButton jbtn : jbil) {
				if (jbtn.isEnabled()) {
					available.add(jbtn);
				}
			}
		}
		return available;
	}

	protected void comTurn() {
		List<JButton> availableCells = getEnabledJButtons();
		JButton chosenOne = availableCells.get(new Random().nextInt(availableCells.size()));
		onTurn(chosenOne, Player.Com);
	}

	protected JButton getJButtonByCoordinates(int[] cell) {
		List<JButton[]> jbl = Arrays.asList(playfield);
		List<JButton> jbil = Arrays.asList(jbl.get(cell[1]));
		return jbil.get(cell[0]);
	}
	
	protected JButton getJButtonByCoordinates(int x, int y) {
		return getJButtonByCoordinates(new int[] {x,y});
	}

	protected int[] getJButtonArrangement(JButton jb) {
		int[] cell = new int[2];
		List<JButton[]> jbl = Arrays.asList(playfield);
		for (JButton[] jba : jbl) {
			List<JButton> jbil = Arrays.asList(jba);
			for (JButton jbtn : jbil) {
				if (jb.equals(jbtn)) {
					cell[0] = jbil.indexOf(jbtn);
					cell[1] = jbl.indexOf(jba);
					return cell;
				}
			}
		}
		System.err.println("Provided JButton is not part of playfield!");
		return null;
	}

	protected void printTurn(JButton jb, Player player) {
		int[] cell = getJButtonArrangement(jb);
		consolePanel.setText(
				consolePanel.getText() + "\nField (" + cell[0] + ", " + cell[1] + ") set by " + player.toString());
	}

	protected void showConsole() {
		scrollPaneCMD.setEnabled(true);
		this.setBounds(100, 100, 200, 400);
		scrollPaneCMD.repaint();
		scrollPaneCMD.revalidate();
		repaint();
		revalidate();
	}

	protected void hideConsole() {
		scrollPaneCMD.setEnabled(false);
		this.setBounds(100, 100, 200, 280);
		scrollPaneCMD.repaint();
		scrollPaneCMD.revalidate();
		repaint();
		revalidate();
	}

	protected void checkConsoleEnabled() {
		if (chkbxConsoleEnabled.isSelected())
			showConsole();
		else
			hideConsole();
	}

	protected void onTurn(JButton jbclicked, Player player) {
		String playername = null;
		switch (player) {
		case Human:
			playername = "X";
			break;
		case Com:
			playername = "O";
			break;
		default:
			playername = null;
			System.out.println("You're stupid!");
			break;
		}
		jbclicked.setText(playername);
		jbclicked.setEnabled(false);
		printTurn(jbclicked, player);
		afterTurn(jbclicked, player);
	}

	protected void reset() {
		for (JButton[] row : playfield) {
			for (JButton jb : row) {
				consolePanel.setText("Neu gestartet");
				jb.setBackground(Color.DARK_GRAY);
				jb.setEnabled(true);
				jb.setText("");
				jb.repaint();
				jb.revalidate();
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public TicTacToe() {
		setBackground(Color.DARK_GRAY);
		setTitle("TicTacToe");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		btn00.setForeground(Color.WHITE);
		btn00.setBackground(Color.DARK_GRAY);

		btn00.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn00.setBounds(10, 11, 50, 50);
		contentPane.add(btn00);
		btn10.setForeground(Color.WHITE);
		btn10.setBackground(Color.DARK_GRAY);

		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn10.setBounds(70, 11, 50, 50);
		contentPane.add(btn10);
		btn20.setForeground(Color.WHITE);
		btn20.setBackground(Color.DARK_GRAY);

		btn20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn20.setBounds(130, 11, 50, 50);
		contentPane.add(btn20);
		btn01.setForeground(Color.WHITE);
		btn01.setBackground(Color.DARK_GRAY);

		btn01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn01.setBounds(10, 72, 50, 50);
		contentPane.add(btn01);
		btn11.setForeground(Color.WHITE);
		btn11.setBackground(Color.DARK_GRAY);

		btn11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);

			}
		});
		btn11.setBounds(70, 72, 50, 50);
		contentPane.add(btn11);
		btn21.setForeground(Color.WHITE);
		btn21.setBackground(Color.DARK_GRAY);

		btn21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn21.setBounds(130, 72, 50, 50);
		contentPane.add(btn21);
		btn02.setForeground(Color.WHITE);
		btn02.setBackground(Color.DARK_GRAY);

		btn02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn02.setBounds(10, 133, 50, 50);
		contentPane.add(btn02);
		btn12.setForeground(Color.WHITE);
		btn12.setBackground(Color.DARK_GRAY);

		btn12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn12.setBounds(70, 133, 50, 50);
		contentPane.add(btn12);
		btn22.setForeground(Color.WHITE);
		btn22.setBackground(Color.DARK_GRAY);

		btn22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTurn((JButton) e.getSource(), Player.Human);
			}
		});
		btn22.setBounds(130, 133, 50, 50);
		contentPane.add(btn22);
		btnRestart.setForeground(Color.WHITE);
		btnRestart.setBackground(Color.GRAY);

		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnRestart.setBounds(10, 194, 170, 23);
		contentPane.add(btnRestart);
		chkbxConsoleEnabled.setForeground(Color.WHITE);
		chkbxConsoleEnabled.setBackground(Color.DARK_GRAY);
		chkbxConsoleEnabled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkConsoleEnabled();
			}
		});

		chkbxConsoleEnabled.setSelected(false);
		chkbxConsoleEnabled.setBounds(10, 224, 170, 23);
		contentPane.add(chkbxConsoleEnabled);
		scrollPaneCMD.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		scrollPaneCMD.setBounds(10, 259, 174, 101);
		contentPane.add(scrollPaneCMD);
		consolePanel.setBorder(null);
		consolePanel.setEditable(false);
		consolePanel.setForeground(Color.WHITE);
		consolePanel.setBackground(Color.GRAY);
		consolePanel.setText("Willkommen");

		scrollPaneCMD.setViewportView(consolePanel);

		checkConsoleEnabled();
	}
}
