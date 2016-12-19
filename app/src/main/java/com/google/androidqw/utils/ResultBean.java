package com.google.androidqw.utils;

import java.util.List;

public  class ResultBean extends BaseModel{
        private String isAttendanceSettings;
        private String workType;
        private String attendanceType;
        private long serviceTime;
        /**
         * departmentUuid : 
         * location : 北京市朝阳区工人体育场东路靠近中国红街1号楼
         * attendanceDate : 1481558400000
         * state : 1
         * companyUuid : 267e0131-a881-42e7-8ac0-72d81447510f
         * attendanceTime : 1481593782000
         * id : 1764
         * attachmentURLs : 
         * description : 
         * cacheIndex : 1481593782916
         * longitude : 39.932848
         * uuid : 7bf33920-6c81-4deb-bbeb-0c1474cc4b64
         * latitude : 116.450726
         * createDate : 1481593782000
         * userUuid : 32ae9e79-b12a-4286-a353-c00dde062441
         * attendanceType : punch
         */

        private List<PunchInfoBean> punchInfo;

        public String getIsAttendanceSettings() {
            return isAttendanceSettings;
        }

        public void setIsAttendanceSettings(String isAttendanceSettings) {
            this.isAttendanceSettings = isAttendanceSettings;
        }

        public String getWorkType() {
            return workType;
        }

        public void setWorkType(String workType) {
            this.workType = workType;
        }

        public String getAttendanceType() {
            return attendanceType;
        }

        public void setAttendanceType(String attendanceType) {
            this.attendanceType = attendanceType;
        }

        public long getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(long serviceTime) {
            this.serviceTime = serviceTime;
        }

        public List<PunchInfoBean> getPunchInfo() {
            return punchInfo;
        }

        public void setPunchInfo(List<PunchInfoBean> punchInfo) {
            this.punchInfo = punchInfo;
        }

        public static class PunchInfoBean {
            private String departmentUuid;
            private String location;
            private long attendanceDate;
            private String state;
            private String companyUuid;
            private long attendanceTime;
            private int id;
            private String attachmentURLs;
            private String description;
            private long cacheIndex;
            private String longitude;
            private String uuid;
            private String latitude;
            private long createDate;
            private String userUuid;
            private String attendanceType;

            public String getDepartmentUuid() {
                return departmentUuid;
            }

            public void setDepartmentUuid(String departmentUuid) {
                this.departmentUuid = departmentUuid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public long getAttendanceDate() {
                return attendanceDate;
            }

            public void setAttendanceDate(long attendanceDate) {
                this.attendanceDate = attendanceDate;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCompanyUuid() {
                return companyUuid;
            }

            public void setCompanyUuid(String companyUuid) {
                this.companyUuid = companyUuid;
            }

            public long getAttendanceTime() {
                return attendanceTime;
            }

            public void setAttendanceTime(long attendanceTime) {
                this.attendanceTime = attendanceTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAttachmentURLs() {
                return attachmentURLs;
            }

            public void setAttachmentURLs(String attachmentURLs) {
                this.attachmentURLs = attachmentURLs;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public long getCacheIndex() {
                return cacheIndex;
            }

            public void setCacheIndex(long cacheIndex) {
                this.cacheIndex = cacheIndex;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public String getUserUuid() {
                return userUuid;
            }

            public void setUserUuid(String userUuid) {
                this.userUuid = userUuid;
            }

            public String getAttendanceType() {
                return attendanceType;
            }

            public void setAttendanceType(String attendanceType) {
                this.attendanceType = attendanceType;
            }
        }
    }