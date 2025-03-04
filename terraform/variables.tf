
variable vpc_cidr_block {
  default = "10.0.0.0/16"
  description = "vpc cidr block"
}
variable subnet_cidr_block {
  default = "10.0.10.0/24"
}
variable availability_zone {
  default = "ap-south-1a"
}
variable env_prefix {
  default = "dev"
}
variable my_ip {
  default = "180.149.212.197/32"
}
variable jenkins_ip {
  default = "64.227.144.52/32"
}
variable instance_type {
  default = "t2.micro"
}
variable region {
  default = "ap-south-1"
}
