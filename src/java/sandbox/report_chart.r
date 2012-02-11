# read data
report <- read.csv("test_locks_report.csv")

# prepare additional variables
specs <- levels(report$Spec)
nspecs <- length(specs)
xrange <- range(report$Number)
yrange <- range(report$Duration)

# set up the plot
plot(xrange, yrange, type="n", xlab="Number of threads", ylab="Duration")
colors <- rainbow(nspecs)
i <- 1

# add lines
for (spec in specs) {
	specset <- subset(report, Spec == spec)
	lines(specset$Number, specset$Duration, type="b", col=colors[i])
	i <- i + 1
}

# add title
title("Fucking chart I am pissed of")

