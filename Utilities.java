import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This class two utility methods for reading/writing images from/to files. This
 * class is not generic. It can only load/save images that have pixel values of
 * type Short.
 * 
 * @author basel Barham
 */
public final class Utilities {

	/**
	 * Method to read the image from the file.
	 * 
	 * @param pgmFile the image file.
	 * @return the image that is loaded.
	 */
	public static Image<Short> loadImage(String pgmFile) {
		

		try {
			// short width;
			// short height;
			File file = new File(pgmFile);
			Scanner scan = new Scanner(file);
			String firstLine = scan.nextLine();
			if (!firstLine.equals("P2")) {
				throw new FileNotFoundException("Wrong file");
			}

			int width = scan.nextInt();
			int height = scan.nextInt();
			Image<Short> image = new Image<>(width, height);
			int thirdLine = scan.nextInt();
			// System.out.println(thirdLine);
			ImageIterator<Short> iterator = new ImageIterator<>(image, Direction.HORIZONTAL);

			// Traverse the image row by row and set pixel values
			/*
			 * for (int row = 0; row < height; row++) { for (int col = 0; col < width;
			 * col++) { if (scan.hasNextShort()) { short pixelValue = scan.nextShort();
			 * iterator.next().setValue(pixelValue); } else { throw new
			 * RuntimeException("Invalid PGM file data"); } } }
			 */

			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					if (scan.hasNext()) {
						short pixelValue = scan.nextShort();
						iterator.next().setValue(pixelValue);
						// System.out.println();
					} else {
						throw new RuntimeException("Invalid PGM file data");
					}
				}
			}

			// Close the scanner
			scan.close();

			return image;

		} catch (FileNotFoundException e) {
			///// finish catch block.
		}

		return null;

	}

	/**
	 * Method to write an image from another image file.
	 * 
	 * @param image   object that want to write.
	 * @param pgmFile image file to read from.
	 */
	public static void saveImage(Image<Short> image, String pgmFile) {
		
		try (PrintWriter fileWriter = new PrintWriter(pgmFile)) {
			// Write the PGM header
			fileWriter.write("P2\n");
			fileWriter.write(image.getWidth() + " " + image.getHeight() + "\n");
			fileWriter.write("255\n");

			// Create an iterator for the image
			ImageIterator<Short> iterator = new ImageIterator<>(image, Direction.HORIZONTAL);

			// Traverse the image row by row and write pixel values
			for (int row = 0; row < image.getHeight(); row++) {
				for (int col = 0; col < image.getWidth(); col++) {
					Short pixelValue = iterator.next().getValue();
					fileWriter.write(pixelValue.toString() + " ");
				}
				//fileWriter.write("\n"); // End of row
			}
		} catch (FileNotFoundException e) {
			// Handle the exception if there's an error while writing the file
			e.printStackTrace();
		}
	}
}
