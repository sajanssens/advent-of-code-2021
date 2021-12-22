package nl.bramjanssens.dec16;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static nl.bramjanssens.util.Util.getLines;

public class App {

    public static void main(String[] args) {
        var hexString = getLines("dec16/input.txt").get(0);
        String bits = hexToBin(hexString);

        Counter position = new Counter(0);
        List<Packet> packets = new ArrayList<>();

        readPackets(bits, position, packets);

        System.out.println(calculateVersionSum(packets));

        Long result = packets.stream().findFirst().map(Packet::getValue).orElse(0L);
        System.out.println(result);
    }

    private static void readPackets(String bits, Counter p, List<Packet> packets) {
        String version = readNextAsDec(bits, p, 3);
        String type = readNextAsDec(bits, p, 3);

        if ("4".equals(type)) {
            packets.add(parseLiteral(bits, p, version, type));
        } else {
            packets.add(parseOperator(bits, p, version, type));
        }
    }

    private static Literal parseLiteral(String bits, Counter p, String version, String type) {
        Literal literal = new Literal();
        literal.version = version;
        literal.type = type;
        String bit1;
        while ((bit1 = readNextAsDec(bits, p, 1)).equals("1")) {
            String bit2to5 = readNextAsBin(bits, p, 4);
            Literal.Bitgroup b = new Literal.Bitgroup(bit1, bit2to5);
            literal.add(b);
        }
        String bit2to5 = readNextAsBin(bits, p, 4);
        Literal.Bitgroup b = new Literal.Bitgroup(bit1, bit2to5);
        literal.add(b);
        return literal;
    }

    private static Operator parseOperator(String bits, Counter p, String version, String type) {
        Operator operator = new Operator();
        operator.version = version;
        operator.type = type;
        operator.lengthType = readNextAsBin(bits, p, 1);

        if (operator.lengthType.equals("0")) {
            operator.lengthOfSubPackets = Integer.parseInt(readNextAsDec(bits, p, 15));
            parseSubPacketsByLength(bits, p, operator);
        } else {
            operator.numberOfSubPackets = Integer.parseInt(readNextAsDec(bits, p, 11));
            parseSubPacketsByCount(bits, p, operator);
        }
        return operator;
    }

    private static void parseSubPacketsByLength(String bits, Counter p, Operator o) {
        var end = p.i + o.lengthOfSubPackets;
        while (p.i < end) {
            readPackets(bits, p, o.subpackets);
        }
    }

    private static void parseSubPacketsByCount(String bits, Counter p, Operator o) {
        while (o.subpackets.size() < o.numberOfSubPackets) {
            readPackets(bits, p, o.subpackets);
        }
    }

    private static String readNextAsDec(String bits, Counter p, int n) {
        String s = binToDec(bits.substring(p.i, p.i + n));
        p.inc(n);
        return s;
    }

    private static String readNextAsBin(String bits, Counter p, int n) {
        String s = bits.substring(p.i, p.i + n);
        p.inc(n);
        return s;
    }

    private static String binToDec(String binaryString) {
        return new BigInteger(binaryString, 2).toString(10);
    }

    private static String hexToBin(String s) {
        // Hack: prepend with "1" to keep leading zero's, and return substring(1)!
        return new BigInteger("1" + s, 16).toString(2).substring(1);
    }

    private static Counter calculateVersionSum(List<Packet> packets) {
        Counter sum = new Counter(0);
        sumVersions(packets, sum);
        return sum;
    }

    private static void sumVersions(List<Packet> packets, Counter sum) {
        for (Packet packet : packets) {
            sum.inc(Integer.parseInt(packet.getVersion()));
            if (packet instanceof Operator o) {
                sumVersions(o.subpackets, sum);
            }
        }
    }
}

@Data @AllArgsConstructor
class Counter {
    int i;

    void inc(int n) {this.i += n;}
}

@Data
abstract class Packet {
    String version;
    String type;

    abstract long getValue();
}

@Data @ToString(callSuper = true)
class Literal extends Packet {

    List<Bitgroup> bitgroups = new ArrayList<>();

    public void add(Bitgroup b) {
        this.bitgroups.add(b);
    }

    @Override long getValue() {
        String value = bitgroups.stream().map(b -> b.nextFourBits).collect(joining());
        return Long.parseLong(value, 2);
    }

    @Data @AllArgsConstructor
    static class Bitgroup {
        String firstBit;
        String nextFourBits;
    }
}

@Data @ToString(callSuper = true)
class Operator extends Packet {
    String lengthType;
    int lengthOfSubPackets;
    int numberOfSubPackets;

    List<Packet> subpackets = new ArrayList<>();

    @Override long getValue() {
        return switch (type) {
            case "0" -> subpackets.stream().mapToLong(Packet::getValue).sum();
            case "1" -> subpackets.stream().mapToLong(Packet::getValue).reduce(1, (subtotal, v) -> subtotal * v);
            case "2" -> subpackets.stream().mapToLong(Packet::getValue).min().orElse(0);
            case "3" -> subpackets.stream().mapToLong(Packet::getValue).max().orElse(0);
            case "5" -> subpackets.get(0).getValue() > subpackets.get(1).getValue() ? 1 : 0;
            case "6" -> subpackets.get(0).getValue() < subpackets.get(1).getValue() ? 1 : 0;
            case "7" -> subpackets.get(0).getValue() == subpackets.get(1).getValue() ? 1 : 0;
            default -> 0;
        };
    }
}
