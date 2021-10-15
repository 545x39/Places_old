package ru.fivefourtyfive.wikimapper.data.datasource.remote.util;

public class PolyPoint {
        private double x;

        private double y;

        public PolyPoint(double paramDouble1, double paramDouble2) {
            this.y = paramDouble1;
            this.x = paramDouble2;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }

        public void setX(double paramDouble) {
            this.x = paramDouble;
        }

        public void setY(double paramDouble) {
            this.y = paramDouble;
        }
    }
