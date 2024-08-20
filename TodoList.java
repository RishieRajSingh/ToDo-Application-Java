import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TodoList {
    private static int count = 0;    // Tracks the number of tasks
    private static JLabel showLabel = new JLabel();     // Displays the number of tasks remaining
    public static void main(String[] args){
        final int width = 40;
        final int height = 35;
        // Creating a JFrame with a title and specified size
        JFrame frame = new JFrame("TODO APPLICATION");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,600);
        frame.setResizable(false);
        

        // Main Panel to hold all the components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        

        // Panel that holds a JLabel, JTextField and a JButton
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        addPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        addPanel.setBackground(new Color(153, 178, 222));
        JLabel title = new JLabel("TODO TASKS");
        title.setPreferredSize(new Dimension(2*width, height));
        // JTextField to input Tasks with alignment, specified size and borders
        JTextField inputTasks = new JTextField("ADD YOUR TASKS HERE");
        inputTasks.setHorizontalAlignment(JTextField.CENTER);
        inputTasks.setPreferredSize(new Dimension(200, height));
        inputTasks.setBorder(BorderFactory.createLineBorder(Color.black));
        // Creating a JButton to add the input Tasks to the list
        JButton addButton = new JButton("+");
        addButton.setPreferredSize(new Dimension(50, height));
        addButton.setBackground(new Color(200,200,200));
        addPanel.add(title);
        addPanel.add(inputTasks);
        addPanel.add(addButton);


        // A JPanel to holds all the tooTasks that are being input from the add Button
        JPanel todoTasks = new JPanel();
        todoTasks.setLayout(new BoxLayout(todoTasks, BoxLayout.Y_AXIS));
        todoTasks.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));    // Adding a margin of 10px to all sides using createEmptyBorder
        todoTasks.setBackground(new Color(200, 200, 200));
        // Adding a vertical scrollbar with a specified size
        JScrollPane scrollPane = new JScrollPane(todoTasks);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(450, 600));


        // A panel to display number of tasks remaining Label and a clear button
        JPanel showPanel = new JPanel();
        showPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        showPanel.setBackground(new Color(250, 140, 50));
        showLabel.setPreferredSize(new Dimension(200, height));
        showLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        showLabel.setHorizontalAlignment(JLabel.CENTER);
        showLabel.setBackground(new Color(200,200,200));

        // Clear button that removes all the tasks
        JButton clear = new JButton("CLEAR");
        clear.setPreferredSize(new Dimension(2*width, height));
        clear.setBackground(new Color(200,200,200));
        showPanel.add(showLabel);
        showPanel.add(clear);
        // Method to update the number of remaining tasks
        updateTasks();

        mainPanel.add(addPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(showPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                // Get the text from the input field
                String tasks = inputTasks.getText();
                if(!tasks.isEmpty() && !tasks.equals("ADD YOUR TASKS HERE")){
                    count++;
                    updateTasks();
                    // Panel to hold a single task
                    JPanel taskPanel = new JPanel();
                    taskPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
                    taskPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
                    taskPanel.setBackground(new Color(200,200,200));

                    JCheckBox check = new JCheckBox();
                    JTextArea taskArea = new JTextArea(tasks, 1, 15); 
                    taskArea.setEditable(false);
                    taskArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    JScrollPane taskScrollPane = new JScrollPane(taskArea);
                    taskScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                    taskScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    taskScrollPane.setPreferredSize(new Dimension(200, height));

                    // Edit button to edit the text
                    JButton edit = new JButton("EDIT");
                    edit.setPreferredSize(new Dimension(2*width, height));
                    edit.setBackground(new Color(25, 160, 195));

                    // A delete button to delete the current task
                    JButton delete = new JButton("DEL");
                    delete.setPreferredSize(new Dimension(2*width,height));
                    delete.setBackground(new Color(250, 30, 35));
                    delete.setForeground(new Color(195, 195, 195));

                    // Adding all components to the taskPanel with the proper gap
                    taskPanel.add(check);
                    taskPanel.add(taskScrollPane);
                    taskPanel.add(edit);
                    taskPanel.add(delete);
                    taskPanel.setMaximumSize(taskPanel.getPreferredSize()); 
                    todoTasks.add(taskPanel);
                    todoTasks.revalidate();
                    todoTasks.repaint();
                    
                    // ActionListener to perform the delete functionality
                    delete.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            count--;
                            updateTasks();
                            todoTasks.remove(taskPanel);
                            todoTasks.revalidate();
                            todoTasks.repaint();
                        }
                    });

                    // ActionListener to strike the task when checked
                    check.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            if(check.isSelected()){
                                Font font = taskArea.getFont();
                                Font italicFont = new Font(font.getName(), Font.ITALIC, font.getSize());
                                taskArea.setFont(italicFont);
                                count--;
                                updateTasks();
                            }
                            else{
                                Font font = taskArea.getFont();
                                Font plainFont = new Font(font.getName(), Font.PLAIN, font.getSize());
                                taskArea.setFont(plainFont);
                                count++;
                                updateTasks();
                            }
                        }
                    });

                    //ActionListener for Edit button
                    edit.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ae){
                            JTextArea taskAr = (JTextArea) taskScrollPane.getViewport().getView();
                            if (taskAr.isEditable()) {
                                taskAr.setEditable(false);
                                edit.setText("EDIT");
                            } else {
                                taskAr.setEditable(true);
                                edit.setText("SAVE");
                            }
                        }
                    });
                }
                inputTasks.setText("");
            }
        });
        // ActionListener to perform the clear Button's functionality
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                count = 0;
                todoTasks.removeAll();
                updateTasks();
                todoTasks.revalidate();
                todoTasks.repaint();
            }
        });
    }
    // A method to update the tasks remaining Label
    private static void updateTasks(){
        showLabel.setText("TASKS REMAINING "+count);
        showLabel.setBackground(new Color(200,200,200));
    }
}