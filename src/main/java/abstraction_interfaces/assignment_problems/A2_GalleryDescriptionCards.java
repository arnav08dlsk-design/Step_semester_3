package abstraction_interfaces.assignment_problems;

abstract class ArtPiece {
    private static int counter = 1000;
    private final String pieceId;
    protected final String title;

    protected ArtPiece(String title) {
        this.title = title;
        this.pieceId = "ART-" + (++counter);
    }

    public abstract String describe();

    String getPieceId() {
        return pieceId;
    }
}

class Painting extends ArtPiece {
    public Painting(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Painting: " + title + ", framed on canvas";
    }
}

class Sculpture extends ArtPiece {
    public Sculpture(String title) {
        super(title);
    }

    @Override
    public String describe() {
        return "Sculpture: " + title + ", carved from stone";
    }
}

public class A2_GalleryDescriptionCards {
    public static void main(String[] args) {
        Painting p = new Painting("Sunset Fields");
        Sculpture s = new Sculpture("The Thinker II");
        System.out.println(p.describe() + " [" + p.getPieceId() + "]");
        System.out.println(s.describe() + " [" + s.getPieceId() + "]");
    }
}
