// 実行用クラス
public class Main {
    public static void main(String[] args) {
        // ユーザーインターフェースのインスタンスを作成してデモ開始
        部屋予約画面 view = new 部屋予約画面();
        view.部屋検索条件を入力する(); // 条件入力
        view.空き部屋一覧を表示する(); // 空き部屋表示
        view.料金を表示する();          // 料金表示
    }
}

// <<boundary>> クラス：ユーザーとのやりとりを担当
class 部屋予約画面 {
    private 部屋予約処理 processor = new 部屋予約処理();

    // 検索条件を入力（スタンダードルーム指定）
    public void 部屋検索条件を入力する() {
        System.out.println("■部屋検索条件を入力しました：スタンダードルーム");
        processor.部屋を検索する("スタンダードルーム");
    }

    // 空き部屋一覧の表示
    public void 空き部屋一覧を表示する() {
        System.out.println("■空き部屋一覧を表示します：");
        processor.空き部屋一覧を取得する();
    }

    // 料金の表示
    public void 料金を表示する() {
        System.out.println("■料金を表示します：");
        processor.料金を確認する("スタンダードルーム");
    }
}

// <<control>> クラス：業務ロジックを管理
class 部屋予約処理 {
    // 部屋を検索し、予約を作成する
    public void 部屋を検索する(String 種別名) {
        部屋種別 種別;
        if (種別名.equals("スタンダードルーム")) {
            種別 = new スタンダードルーム();
        } else {
            種別 = new スイートルーム();
        }

        int 空き部屋数 = 種別.空き部屋数を取得する();
        int 値段 = 種別.値段を取得する();

        System.out.println("空き部屋数: " + 空き部屋数);
        System.out.println("料金: " + 値段);

        // 予約作成
        予約 y = new 予約("20250626", "池田真史", "xxx.xxx@akane.waseda.jp", 501);
        y.作成する();
    }

    // 空き部屋一覧を取得して表示する
    public void 空き部屋一覧を取得する() {
        部屋部屋一覧一覧(); // ダミー表示
    }

    // 料金を確認する
    public void 料金を確認する(String 種別名) {
        部屋種別 種別 = 種別名.equals("スタンダードルーム") ? new スタンダードルーム() : new スイートルーム();
        System.out.println("料金: " + 種別.値段を取得する() + "円");
    }

    // 空き部屋一覧のダミー出力
    private void 部屋部屋一覧一覧() {
        System.out.println("部屋番号: 501 - スタンダードルーム");
        System.out.println("部屋番号: 502 - スタンダードルーム");
        System.out.println("部屋番号: 601 - スイートルーム");
    }
}

// <<entity>> 予約クラス
class 予約 {
    private String 日付;
    private String 顧客の名前;
    private String 顧客のメールアドレス;
    private int 部屋番号;

    // コンストラクタ
    public 予約(String 日付, String 名前, String メール, int 番号) {
        this.日付 = 日付;
        this.顧客の名前 = 名前;
        this.顧客のメールアドレス = メール;
        this.部屋番号 = 番号;
    }

    // 予約作成メソッド
    public void 作成する() {
        System.out.println("■予約情報：");
        System.out.println("日付：" + 日付);
        System.out.println("名前：" + 顧客の名前);
        System.out.println("メール：" + 顧客のメールアドレス);
        System.out.println("部屋番号：" + 部屋番号);
    }
}

// <<entity>> 抽象クラス：部屋種別
abstract class 部屋種別 {
    protected int 空き部屋数;
    protected int 値段;

    public int 空き部屋数を取得する() {
        return 空き部屋数;
    }

    public int 値段を取得する() {
        return 値段;
    }
}

// <<entity>> スタンダードルーム（部屋種別を継承）
class スタンダードルーム extends 部屋種別 {
    public スタンダードルーム() {
        this.値段 = 5000;
        this.空き部屋数 = 10;
    }
}

// <<entity>> スイートルーム（部屋種別を継承）
class スイートルーム extends 部屋種別 {
    public スイートルーム() {
        this.値段 = 10000;
        this.空き部屋数 = 3;
    }
}

// <<entity>> 部屋（基本クラス）
class 部屋 {
    protected int 部屋番号;

    public 部屋(int 番号) {
        this.部屋番号 = 番号;
    }

    public int get部屋番号() {
        return 部屋番号;
    }
}
