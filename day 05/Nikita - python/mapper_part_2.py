from dataclasses import dataclass, field
from typing import List
from range import Range


@dataclass
class Mapper:
    ranges: List[Range] = field(default_factory=list)

    def add_range(self, new_range: Range):
        self.ranges.append(new_range)

    def add_range_from_string_numbers(self, source_range: int, target_range: int, length: int):
        self.add_range(Range(source_range, target_range, length))

    def map_source_to_target(self, source_value: int) -> int:
        for i, range_inner in enumerate(self.ranges):
            if range_inner.is_in_range(source_value):
                return range_inner.map(source_value)
        return source_value


@dataclass
class MapperBuilder:
    map_dict: dict = field(default_factory=dict)
    map_to_map_dict: dict = field(default_factory=dict)
    seeds: List[int] = field(default_factory=list)

    @staticmethod
    def _read_seeds(line: str):
        seeds = line.split(":")[1]
        seeds = seeds.strip()
        return [int(seed) for seed in seeds.split(" ")]

    def _add_map_definition(self, line: str):
        line = line.split(" ")[0]
        source, target = line.split("-to-")
        self.map_to_map_dict[source] = target
        self.map_dict[source] = Mapper()
        return source

    def create_map_for_input(self, path: str):
        current_map = None
        with open(path) as f:
            for line in f.read().split("\n"):
                if line == "":
                    continue
                elif "seeds" in line:
                    self.seeds = self._read_seeds(line)
                elif "map" in line:
                    current_map = self._add_map_definition(line)
                else:
                    target, source, length = line.split(" ")
                    self.map_dict[current_map].add_range_from_string_numbers(int(source), int(target), int(length))

    def map_seeds_to_location(self):
        all_locations = []
        for seed_iter in self.seeds:
            all_locations.append(self.map_seed_to_location(seed_iter))
        return all_locations

    def map_seed_to_location(self, seed_inner):
        print(f"Mapping seed {seed_inner}")
        current_source = "seed"
        current_destination = None
        mapped_value = seed_inner
        while current_destination != "location":
            current_destination = self.map_to_map_dict[current_source]
            mapped_value = self.map_dict[current_source].map_source_to_target(mapped_value)
            current_source = current_destination
        return mapped_value


if __name__ == '__main__':
    mapper_builder = MapperBuilder()
    mapper_builder.create_map_for_input("input.txt")
    locations = mapper_builder.map_seeds_to_location()
    print(min(locations))
