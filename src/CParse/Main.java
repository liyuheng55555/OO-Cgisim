package CParse;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Main extends Application {



    //把string类型转换成图片
    public static BufferedImage base64StringToImg(final String base64String) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage result = ImageIO.read(bais);
            if (result != null) {
                System.out.println(result.getWidth()+" "+ result.getHeight());
                ImageIO.write(result, "png", new File("result/str.png"));
            }
            return ImageIO.read(bais);
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }


    static BufferedImage compress_(BufferedImage image, double comp) {
        int w = (int) (image.getWidth()*comp);
        int h = (int) (image.getHeight()*comp);
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                result.setRGB(i,j,image.getRGB((int) (i/comp*0.95), (int) (j/comp*0.95)));
            }
        }
        return result;
    }
    static BufferedImage getImage(ArrayList<ArrayList<Type>> arr) throws IOException {
        BufferedImage end              = ImageIO.read(new File("image1/end.png"              ));
        BufferedImage start            = ImageIO.read(new File("image1/start.png"            ));
        BufferedImage merge            = ImageIO.read(new File("image1/merge.png"            ));
        BufferedImage branch           = ImageIO.read(new File("image1/if.png"               ));
        BufferedImage vertical         = ImageIO.read(new File("image1/竖线.png"               ));
        BufferedImage horizon          = ImageIO.read(new File("image1/横线.png"               ));
        BufferedImage down_left        = ImageIO.read(new File("image1/左上.png"               ));
        BufferedImage right_down       = ImageIO.read(new File("image1/左下.png"               ));
        BufferedImage statement        = ImageIO.read(new File("image1/statement.png"            ));
        BufferedImage loop_start_left  = ImageIO.read(new File("image1/loop start left.png"      ));
        BufferedImage loop_start_right = ImageIO.read(new File("image1/loop start right.png" ));
        BufferedImage loop_start_mid   = ImageIO.read(new File("image1/loop start mid.png"   ));
        BufferedImage loop_end_left    = ImageIO.read(new File("image1/loop end left.png"    ));
        BufferedImage loop_end_right   = ImageIO.read(new File("image1/loop end right.png"   ));
        BufferedImage loop_end_mid     = ImageIO.read(new File("image1/loop end mid.png"     ));
        BufferedImage loop_body        = ImageIO.read(new File("image1/loop body.png"        ));

        int w = end.getWidth();
        int h = end.getHeight();

        // result尺寸不超过 10000×10000
        double compress = 1;
        if (h * arr.size() > 10000) {
            compress = 10000. / (h * arr.size());
        }
        BufferedImage result = new BufferedImage(
                (int) (w * arr.get(0).size() * compress) + 5,
                (int) (h * arr.size() * compress) + 5,
                BufferedImage.TYPE_INT_ARGB
        );

        w *= compress;
        h *= compress;

        end           = compress_(end          , compress);
        start         = compress_(start        , compress);
        merge         = compress_(merge        , compress);
        branch        = compress_(branch       , compress);
        vertical      = compress_(vertical     , compress);
        horizon       = compress_(horizon      , compress);
        down_left     = compress_(down_left    , compress);
        right_down    = compress_(right_down   , compress);
        statement     = compress_(statement    , compress);
        loop_start_left   = compress_(loop_start_left  , compress);
        loop_start_right  = compress_(loop_start_right , compress);
        loop_start_mid    = compress_(loop_start_mid   , compress);
        loop_end_left     = compress_(loop_end_left    , compress);
        loop_end_right    = compress_(loop_end_right   , compress);
        loop_end_mid      = compress_(loop_end_mid     , compress);
        loop_body         = compress_(loop_body        , compress);


        for (int i=0; i<arr.size(); i++) {
            for (int j=0; j<arr.get(0).size(); j++) {
                BufferedImage buf = null;
                switch (arr.get(i).get(j)) {
                    case EMPTY: continue;
                    case START: buf = start; break;
                    case END: buf = end; break;
                    case BRANCH: buf = branch; break;
                    case MERGE: buf = merge; break;
                    case VERTICAL: buf = vertical; break;
                    case HORIZON: buf = horizon; break;
                    case 下转左: buf = down_left ; break;
                    case 左转下: buf = right_down; break;
                    case STATEMENT: buf = statement; break;
                    case LOOP_START_LEFT: buf = loop_start_left  ; break;
                    case LOOP_START_RIGHT: buf = loop_start_right ; break;
                    case LOOP_START_MID: buf = loop_start_mid   ; break;
                    case LOOP_END_LEFT: buf = loop_end_left    ; break;
                    case LOOP_END_RIGHT: buf = loop_end_right   ; break;
                    case LOOP_END_MID: buf = loop_end_mid     ; break;
                    case LOOP_ARROW: buf = loop_body        ; break;
                    default:
                        System.out.println("不识别"+arr.get(i).get(j));
                        return null;
                }
                for (int k=0; k<h; k++) {
                    for (int l=0; l<w; l++) {
                        result.setRGB(
                                j*w + l,
                                i*h + k,
                                buf.getRGB(l, k));
                    }
                }
            }
        }
        
        return result;
    }

    static int H = 2000;
    static int W = 2000;
    static int viewH = 100;
    static int viewW = 161;
    static AnchorPane getImage2(ArrayList<ArrayList<ImageView>> arr) {
        AnchorPane drawArea = new AnchorPane();
        drawArea.setLayoutX(arr.get(0).size()*viewW*2);
        drawArea.setLayoutY(arr.size()*viewH*2);
        for (int i=0; i<arr.size(); i++) {
            for (int j=0; j<arr.get(0).size(); j++) {
                ImageView view = arr.get(i).get(j);
                view.setX(viewW*j);
                view.setY(viewH*i);
                view.setFitHeight(viewH);
                view.setFitWidth(viewW);
                drawArea.getChildren().add(view);
                if (view.getId()!=null) {
                    Text text = new Text();
                    text.setText(view.getId());
                    text.setFont(new Font("Arial", 18));
                    text.setX(viewW*j+viewW*0.3);
                    text.setY(viewH*i+viewH*0.55);
                    drawArea.getChildren().add(text);
                }
            }
        }
        return drawArea;
    }

    static File sourceFile = null;
//    static File targetFile = null;

    static public void setFile(File srcFile) {
        sourceFile = srcFile;
//        targetFile = tgtFile;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("start");
        setup();
        StringBuilder builder = new StringBuilder();
//        File file = new File("example/e2.c");
        File file = sourceFile;
        InputStream input = new FileInputStream(file);
        while (true) {
            int b = input.read();
            if (b==-1) break;
            builder.append((char)b);
        }
        input.close();
        System.out.println(builder.toString());

        CodePointCharStream cpcs = CharStreams.fromString(builder.toString());
        simpleCLexer lexer = new simpleCLexer(cpcs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        simpleCParser parser = new simpleCParser(tokens);
        simpleCParser.ProgContext progContext = parser.prog();
        Eval eval = new Eval();
        Eval2 eval2 = new Eval2();
        ArrayList<ArrayList<Type>> arr = eval.visit(progContext);
        for (ArrayList<Type> ar : arr)
            System.out.println(ar);
//        BufferedImage result = getImage(arr);
//        ImageIO.write(result, "png", new File("result/result.png"));
        AnchorPane drawArea = getImage2(eval2.visit(progContext));
        SnapshotParameters sp =  new SnapshotParameters();
        sp.setTransform(Transform.scale(2, 2));
        if (drawArea==null) {
            System.out.println();
        }
        WritableImage image = drawArea.snapshot(sp, null);
        ByteArrayOutputStream  byteOutput = new ByteArrayOutputStream();
        System.out.println(sourceFile.getParent());
        ImageIO.write( SwingFXUtils.fromFXImage( image, null ), "png", new File(sourceFile.getParent()+"\\"+sourceFile.getName()+".png") );
        System.out.println("finish!");
        stop();
//        ImageIO.write((RenderedImage) image, "png", new File("result/result2.png"));
    }

    static void setup() {
        Type2.END.setImage  (new Image("sources/image/end.png"             ));
        Type2.START.setImage(new Image("sources/image/start.png"           ));
        Type2.MERGE.setImage(new Image("sources/image/merge.png"           ));
        Type2.BRANCH.setImage(new Image("sources/image/if.png"              ));
        Type2.VERTICAL.setImage(new Image("sources/image/竖线.png"              ));
        Type2.HORIZON.setImage(new Image("sources/image/横线.png"              ));
        Type2.下转左.setImage(new Image("sources/image/左上.png"              ));
        Type2.左转下.setImage(new Image("sources/image/左下.png"              ));
        Type2.STATEMENT.setImage(new Image("sources/image/statement.png"       ));
        Type2.LOOP_START_LEFT.setImage(new Image("/sources/image/loop start left.png" ));
        Type2.LOOP_START_RIGHT.setImage(new Image("sources/image/loop start right.png"));
        Type2.LOOP_START_MID.setImage(new Image("sources/image/loop start mid.png"  ));
        Type2.LOOP_END_LEFT.setImage(new Image("sources/image/loop end left.png"   ));
        Type2.LOOP_END_RIGHT.setImage(new Image("sources/image/loop end right.png"  ));
        Type2.LOOP_END_MID.setImage(new Image("sources/image/loop end mid.png"    ));
        Type2.LOOP_ARROW.setImage(new Image("sources/image/loop body.png"       ));
        Type2.EMPTY.setImage(new Image("sources/image/empty.png"));
    }

    public static void main(String[] args) throws Exception {
        launch();

    }
}
