package view_controller;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.UserAccount;

/**
 * This class create statistics view for Wordle Game.
 * @author UBIS
 *
 */
public class StatsViewV2 extends BorderPane
{
	private UserAccount user;
	private final static String win="Wins", lost="Losses", total="Total Games Playes";
	private Main parent;
	private VBox innerPane;
	
	/**
	 * This is a constructor for StatsViewV2.
	 * @param user UserAccount
	 * @param parent Main Application, parent.
	 */
	public StatsViewV2(UserAccount user, Main parent)
	{
		super();
		this.parent=parent;
		this.user=user;
		innerPane=new VBox(10);
		createFunc();
		createChart();
		createLineGraph();
		this.setCenter(innerPane);
	}
	
	private void createFunc()
	{
		HBox funcPane=new HBox(5);
		
		Button button=new Button("Back");
		button.setOnAction(e->parent.createGameView());
		Button saveImage=new Button("Save Image");
		saveImage.setOnAction((e)->{saveImage();});

		
		funcPane.getChildren().addAll(saveImage, button);
		
		this.setTop(funcPane);
		funcPane.setPadding(new Insets(5));
		this.setAlignment(funcPane, Pos.CENTER);
		
	}
	
	private void saveImage()
	{
		WritableImage image = this.snapshot(null, null);
	    File file = new File("images/statistics.png");
	    try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    showWarning("Saved in the images folder!");
	}
	
	
	private void createChart()
	{
		final NumberAxis xAxis=new NumberAxis(0, user.getGamesPlayed()+1, 1);
		final CategoryAxis yAxis=new CategoryAxis();
		BarChart<Number, String> bc= new BarChart<Number,String>(xAxis, yAxis);
		bc.setTitle("User Statistics");
		xAxis.setLabel("Numebr of Games");
		xAxis.setTickLabelRotation(90);
		yAxis.setLabel("Category");
		
		XYChart.Series series=new XYChart.Series();
		series.setName("Games");
		series.getData().add(new XYChart.Data<>(user.getGamesPlayed(), total));
		series.getData().add(new XYChart.Data<>(user.getGamesWon(), win));
		series.getData().add(new XYChart.Data<>(user.getGamesPlayed()-user.getGamesWon(), lost));
		
		bc.getData().addAll(series);
		innerPane.getChildren().add(bc);
	}
	
	private void createLineGraph()
	{
		final NumberAxis xAxis=new NumberAxis(1, user.getAttempt().size()+1, 1);
		final NumberAxis yAxis=new NumberAxis();
		
		xAxis.setLabel("Win Number");
		yAxis.setLabel("Number of Attempts");
		
		
		final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);

		lineChart.setTitle("Attempts for Win");
		XYChart.Series series = new XYChart.Series();
		series.setName("All Wins");
		for(int i=1; i<=user.getAttempt().size(); i++ )
		{
			series.getData().add(new XYChart.Data(i, user.getAttempt().get(i-1)));
			
		}
		
		lineChart.getData().add(series);
		innerPane.getChildren().add(lineChart);
	}
	
	private void showWarning(String warning) {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Warning");
		ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.setContentText(warning);
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();
	}
	
}
