class SegmentTree {
    long[] sum;
    int[] min;
    int[] max;
    int size;

    //После создания экземпляра класса, нужно будет ещё инициализировать дерево
    //При помощи функции initArray
    SegmentTree(int n) {
        this.size = n;
        int z = n * 4;
        this.sum = new long[z];
        this.min = new int[z];
        this.max = new int[z];
        Arrays.fill(this.sum, 0);
        Arrays.fill(this.min, Integer.MAX_VALUE);
        Arrays.fill(this.max, Integer.MIN_VALUE);
    }

    int[] a;

    // a - ссылка на массив, мы не перекопируем его
    // Эту функцию включаем из основного кода
    void initArray(int[] a) {
        this.a = a;
        buildTree(1, 0, size - 1);
    }

    // Рекурсивно спускаемся вниз, right не больше, чем размер дерева.
    // Эта функция вызывается только внутри дерева
    private void buildTree(int v, int left, int right) {
        if (right == left) {
            sum[v] = a[left];
            min[v] = a[left];
            max[v] = a[left];
        } else {
            int mid = (left + right) / 2;
            // Вычисляем детей нашей вершины.
            // В этой реализации в 1 находится "голова" дерева
            int vleft = (v * 2), vrigth = vleft + 1;
            buildTree(vleft, left, mid);
            buildTree(vrigth, mid + 1, right);
            updateVertex(v, vleft, vrigth);
        }
    }

    // Обновления значения в вершине из двух её детей
    // Вызывается только внутри дерева
    private void updateVertex(int v, int vleft, int vright) {
        max[v] = Math.max(max[vleft], max[vright]);
        min[v] = Math.min(min[vleft], min[vright]);
        sum[v] = sum[vleft] + sum[vright];
    }

    // Завели переменные, чтобы не передавать их каждый раз в функции
    // и везде писать v, left, right
    int value, index;

    // Обновление значения элемента массива по индексу
    // В данном случае начачальный массив не изменяем,
    // но при необходимости делать это не сложно
    void updateIndex(int index, int value) {
        this.value = value;
        this.index = index;
        updateTree(1, 0, size - 1);
    }

    // Функция, которая находит, где лежит нужный нам элемент и меняет его
    // По пути обновляя значения в вершинах
    private void updateTree(int v, int left, int right) {
        if (right == left) {
            min[v] = value;
            max[v] = value;
            sum[v] = value;
        } else {
            int mid = (right + left) / 2;
            int vleft = v * 2, vright = vleft + 1;
            if (index <= mid)
                updateTree(vleft, left, mid);
            else
                updateTree(vright, mid + 1, right);

            updateVertex(v, vleft, vright);
        }
    }

    // Переменные для результата и запроса
    // Результат можно и возвращать из рекурсии,
    // но когда нужно вернуть сразу много всего удобно сделать так
    int start, end;
    int resMax, resMin;
    long resSum;

    // Функция которую будем запускать из основного кода
    // В ней "обнуляем" значения ответных переменных
    // (для некоторых функций нужно будет подумать как конкретно это сделать)
    void getSegment(int start, int end) {
        this.start = start;
        this.end = end;
        this.resMax = Integer.MIN_VALUE;
        this.resMin = Integer.MAX_VALUE;
        this.resSum = 0L;
        getTreeSegment(1, 0, size - 1);
    }

    // Получаем наши ответы
    private void getTreeSegment(int v, int left, int right) {
        if (start <= left && right <= end) {
            resMin = Math.min(min[v], resMin);
            resMax = Math.max(max[v], resMax);
            resSum = sum[v] + resSum;
        } else {
            int mid = (right + left) / 2;
            int vleft = v * 2, vright = vleft + 1;
            if (start <= mid)
                getTreeSegment(vleft, left, mid);
            if (mid < end)
                getTreeSegment(vright, mid + 1, right);
        }
    }
}

void solve() {
    int n;
    int[] a;

    // прочитали

    // создали дерево
    SegmentTree tree = new SegmentTree(n);
    tree.initArray(a);

    int left, right;
    int i, value;

    // присвоили по индексу массива i значение value
    tree.updateIndex(i, value);

    // получаем ответ на отрезке
    tree.getSegment(left, right);
    int min = tree.resMin;
    int max = tree.resMax;
    long sum = tree.resSum;
}
