package com.hs.rest.gps.util;

import java.math.BigDecimal;

/**
 * 根据球面距离公式计算两个地点之间的距离
 *
 * <p>公式：S=R·arccos[cosβ1·cosβ·2cos(α1-α2)+sinβ1·sinβ2]
 */
public class DistanceUtil {

  /** 地球半径（单位：米） */
  private static final double EARTH_RADIUS = 6371393;

  /** 以度为单位的角度值乘以该常数以获得以弧度为单位的角度值。 */
  private static final double DEGREES_TO_RADIANS = 0.017453292519943295;

  /**
   * 根据公式进行计算
   *
   * @param longitude1 位置1经度
   * @param latitude1 位置1纬度
   * @param longitude2 位置2经度
   * @param latitude2 位置2纬度
   * @return 返回计算的距离，单位：米
   */
  public static double getDistance(
      Double longitude1, Double latitude1, Double longitude2, Double latitude2) {
    double radiansLongitude1 = toRadians(longitude1);
    double radiansLatitude1 = toRadians(latitude1);
    double radiansLongitude2 = toRadians(longitude2);
    double radiansLatitude2 = Math.toRadians(latitude2);

    final double cos =
        BigDecimal.valueOf(Math.cos(radiansLatitude1))
            .multiply(BigDecimal.valueOf(Math.cos(radiansLatitude2)))
            .multiply(
                BigDecimal.valueOf(
                    Math.cos(
                        BigDecimal.valueOf(radiansLongitude1)
                            .subtract(BigDecimal.valueOf(radiansLongitude2))
                            .doubleValue())))
            .add(
                BigDecimal.valueOf(Math.sin(radiansLatitude1))
                    .multiply(BigDecimal.valueOf(Math.sin(radiansLatitude2))))
            .doubleValue();

    double acos = Math.acos(cos);
    return BigDecimal.valueOf(EARTH_RADIUS).multiply(BigDecimal.valueOf(acos)).doubleValue();
  }

  /**
   * 参考：{@link Math#toRadians(double)}
   *
   * @param value value
   * @return {double}
   */
  private static double toRadians(double value) {
    return BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(DEGREES_TO_RADIANS)).doubleValue();
  }

  /**
   * 测试
   *
   * <pre>
   * "診所": 桃園市桃園區新生路64、66號：121.30977,24.99782
   * "電腦所在地": 桃園市桃園區中正路176號7樓：121.30668,25.00195
   * </pre>
   *
   * @param args args
   */
  public static void main(String[] args) {
    double distance = getDistance(121.30977, 24.99782, 121.30668, 25.00195);
    System.out.println(distance + " m");
  }
}
