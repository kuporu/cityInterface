package org.hgc.cityRepository.service.Support;

public class SimpleRedisKey implements RedisKey{
    @Override
    public Object generateRedisKey(int... args) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = args.length;
        for (int i = 0; i < len; i++) {
            stringBuilder.append(args[i]);
            if (i != len - 1) {
                stringBuilder.append("/");
            }
        }

        return stringBuilder.toString();
    }
}
