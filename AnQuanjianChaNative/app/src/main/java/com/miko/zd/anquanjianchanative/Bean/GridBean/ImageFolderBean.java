    package com.miko.zd.anquanjianchanative.Bean.GridBean;

    /**
     * Created by zd on 2016/4/24.
     */
    public class ImageFolderBean {
        private String FolderPath;
        private Integer FileNumber;
        private String Name;
        private String FirstImagePath;

        public String getFirstImagePath() {
            return FirstImagePath;
        }

        public void setFirstImagePath(String firstFilePath) {
            FirstImagePath = firstFilePath;
        }

        public String getFolderPath() {
            return FolderPath;
        }

        public void setFolderPath(String folderPath) {
            FolderPath = folderPath;
        }

        public Integer getFileNumber() {
            return FileNumber;
        }

        public void setFileNumber(Integer fileNumber) {
            FileNumber = fileNumber;
        }

        public String getName() {
            return Name;
        }

        public void setName(String fileName) {
            Name = fileName;
        }
    }
