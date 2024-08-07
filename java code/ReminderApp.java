import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.text.SimpleDateFormat;
public class ReminderApp extends JFrame {
    private JComboBox<String> dayComboBox;
    private JComboBox<String> activityComboBox;
    private JSpinner timeSpinner;
    private Timer timer;

    public ReminderApp() {
        setTitle("Reminder App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Day ComboBox
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        dayComboBox = new JComboBox<>(days);
        add(new JLabel("Select Day:"));
        add(dayComboBox);

        // Time Spinner
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        add(new JLabel("Select Time:"));
        add(timeSpinner);

        // Activity ComboBox
        String[] activities = {"Wake up", "Go to gym", "Breakfast", "Meetings", "Lunch", "Quick nap", "Go to library", "Dinner", "Go to sleep"};
        activityComboBox = new JComboBox<>(activities);
        add(new JLabel("Select Activity:"));
        add(activityComboBox);

        // Set Reminder Button
        JButton setReminderButton = new JButton("Set Reminder");
        setReminderButton.addActionListener(new SetReminderAction());
        add(setReminderButton);

        timer = new Timer();

        setVisible(true);
    }

    private class SetReminderAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedDay = (String) dayComboBox.getSelectedItem();
            Date selectedTime = (Date) timeSpinner.getValue();
            String selectedActivity = (String) activityComboBox.getSelectedItem();

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            
            Date now = new Date();
            String currentDay = dayFormat.format(now);

            if (!selectedDay.equals(currentDay)) {
                JOptionPane.showMessageDialog(null, "Reminder can only be set for the current day.");
                return;
            }

            Date currentTime = new Date();
            if (selectedTime.before(currentTime)) {
                JOptionPane.showMessageDialog(null, "Reminder time must be in the future.");
                return;
            }

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "Time for: " + selectedActivity);
                }
            }, selectedTime);
        }
    }

    public static void main(String[] args) {
        new ReminderApp();
    }
}
