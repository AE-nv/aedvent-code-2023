from dataclasses import dataclass


@dataclass
class Range:
    start: int
    destination: int
    length: int

    def is_in_range(self, value):
        return self.start <= value <= self.start + self.length - 1

    def map(self, value):
        return self.destination + (value - self.start)

    def __contains__(self, item):
        return self.is_in_range(item)

    def __le__(self, other):
        return self.start <= other.start

    def __lt__(self, other):
        return self.start < other.start

    def union(self, other):
        if self.start <= other.start:
            return Range(self.start, self.destination, self.length + other.length)
        else:
            return Range(other.start, other.destination, self.length + other.length)


@dataclass
class DumbRange:
    start: int
    end: int

    def __eq__(self, other):
        return self.start == other.start and self.end == other.end