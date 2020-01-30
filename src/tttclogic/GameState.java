package tttclogic;

public enum GameState {
    WIN , DRAW /*{
        @Override
        public void state() {
            new Controller().paneImage.setVisible(true);
            try {
                new ImageView(new Image(new FileInputStream("draw.jpg")));
            } catch (FileNotFoundException e) {
                System.out.println("О БОЖИ ОШИБКА В ЕНУМЕ НИЧЬЕЙ");
            }
            new Controller().congrat.setText("Ничья");
        }

    }*/
}
