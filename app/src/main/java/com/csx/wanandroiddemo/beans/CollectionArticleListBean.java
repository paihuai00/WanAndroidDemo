package com.csx.wanandroiddemo.beans;

import java.util.List;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description:
 */
public class CollectionArticleListBean {


    /**
     * data : {"curPage":1,"datas":[{"author":"Android群英传","chapterId":413,"chapterName":"Android群英传","courseId":13,"desc":"","envelopePic":"","id":47825,"link":"http://mp.weixin.qq.com/s?__biz=MzAxNzMxNzk5OQ==&mid=2649485884&idx=1&sn=dcaf70662a2c5b3290c06e6add85e259&chksm=83f83b3cb48fb22abfba1bcfc34fcebfae4e1714ed2ebca0bb9facec5d72d4615a05937c1f06&scene=38#wechat_redirect","niceDate":"2小时前","origin":"","originId":5249,"publishTime":1551079736000,"title":"分享图片","userId":17631,"visible":0,"zan":0},{"author":" coder-pig","chapterId":60,"chapterName":"Android Studio相关","courseId":13,"desc":"","envelopePic":"","id":47773,"link":"https://juejin.im/post/5c09f9daf265da61193ba4f2","niceDate":"3小时前","origin":"","originId":7654,"publishTime":1551076719000,"title":"逮虾户！Android程序调试竟简单如斯","userId":17631,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":47772,"link":"https://mp.weixin.qq.com/s/y5SaYprZJqhUmWy8a9qkKQ","niceDate":"3小时前","origin":"","originId":7925,"publishTime":1551076698000,"title":"最后一篇啦，快来快来~","userId":17631,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":47771,"link":"https://mp.weixin.qq.com/s/ya0RiLuHfIBrPLkl2lTbaA","niceDate":"3小时前","origin":"","originId":7957,"publishTime":1551076695000,"title":"&ldquo;丧心病狂&rdquo;的混淆操作！","userId":17631,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":47770,"link":"https://mp.weixin.qq.com/s/nn-nwXnRI9JYSmknH1pzYg","niceDate":"3小时前","origin":"","originId":7972,"publishTime":1551076692000,"title":"再&ldquo;丧心病狂&rdquo;的混淆也不怕","userId":17631,"visible":0,"zan":0},{"author":" 张朝旭","chapterId":431,"chapterName":"Notification ","courseId":13,"desc":"","envelopePic":"","id":47340,"link":"https://juejin.im/post/5c287e1ae51d453634703b15","niceDate":"2019-02-21","origin":"","originId":7938,"publishTime":1550721186000,"title":"Android 深入理解 Notification 机制","userId":17631,"visible":0,"zan":0}],"offset":0,"over":true,"pageCount":1,"size":20,"total":6}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * curPage : 1
         * datas : [{"author":"Android群英传","chapterId":413,"chapterName":"Android群英传","courseId":13,"desc":"","envelopePic":"","id":47825,"link":"http://mp.weixin.qq.com/s?__biz=MzAxNzMxNzk5OQ==&mid=2649485884&idx=1&sn=dcaf70662a2c5b3290c06e6add85e259&chksm=83f83b3cb48fb22abfba1bcfc34fcebfae4e1714ed2ebca0bb9facec5d72d4615a05937c1f06&scene=38#wechat_redirect","niceDate":"2小时前","origin":"","originId":5249,"publishTime":1551079736000,"title":"分享图片","userId":17631,"visible":0,"zan":0},{"author":" coder-pig","chapterId":60,"chapterName":"Android Studio相关","courseId":13,"desc":"","envelopePic":"","id":47773,"link":"https://juejin.im/post/5c09f9daf265da61193ba4f2","niceDate":"3小时前","origin":"","originId":7654,"publishTime":1551076719000,"title":"逮虾户！Android程序调试竟简单如斯","userId":17631,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":47772,"link":"https://mp.weixin.qq.com/s/y5SaYprZJqhUmWy8a9qkKQ","niceDate":"3小时前","origin":"","originId":7925,"publishTime":1551076698000,"title":"最后一篇啦，快来快来~","userId":17631,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":47771,"link":"https://mp.weixin.qq.com/s/ya0RiLuHfIBrPLkl2lTbaA","niceDate":"3小时前","origin":"","originId":7957,"publishTime":1551076695000,"title":"&ldquo;丧心病狂&rdquo;的混淆操作！","userId":17631,"visible":0,"zan":0},{"author":"鸿洋","chapterId":408,"chapterName":"鸿洋","courseId":13,"desc":"","envelopePic":"","id":47770,"link":"https://mp.weixin.qq.com/s/nn-nwXnRI9JYSmknH1pzYg","niceDate":"3小时前","origin":"","originId":7972,"publishTime":1551076692000,"title":"再&ldquo;丧心病狂&rdquo;的混淆也不怕","userId":17631,"visible":0,"zan":0},{"author":" 张朝旭","chapterId":431,"chapterName":"Notification ","courseId":13,"desc":"","envelopePic":"","id":47340,"link":"https://juejin.im/post/5c287e1ae51d453634703b15","niceDate":"2019-02-21","origin":"","originId":7938,"publishTime":1550721186000,"title":"Android 深入理解 Notification 机制","userId":17631,"visible":0,"zan":0}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 6
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * author : Android群英传
             * chapterId : 413
             * chapterName : Android群英传
             * courseId : 13
             * desc :
             * envelopePic :
             * id : 47825
             * link : http://mp.weixin.qq.com/s?__biz=MzAxNzMxNzk5OQ==&mid=2649485884&idx=1&sn=dcaf70662a2c5b3290c06e6add85e259&chksm=83f83b3cb48fb22abfba1bcfc34fcebfae4e1714ed2ebca0bb9facec5d72d4615a05937c1f06&scene=38#wechat_redirect
             * niceDate : 2小时前
             * origin :
             * originId : 5249
             * publishTime : 1551079736000
             * title : 分享图片
             * userId : 17631
             * visible : 0
             * zan : 0
             */

            private String author;
            private int chapterId;
            private String chapterName;
            private int courseId;
            private String desc;
            private String envelopePic;
            private int id;
            private String link;
            private String niceDate;
            private String origin;
            private int originId;
            private long publishTime;
            private String title;
            private int userId;
            private int visible;
            private int zan;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public int getOriginId() {
                return originId;
            }

            public void setOriginId(int originId) {
                this.originId = originId;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }
        }
    }
}
